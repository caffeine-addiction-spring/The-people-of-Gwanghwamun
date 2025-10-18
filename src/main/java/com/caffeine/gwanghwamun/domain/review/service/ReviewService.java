package com.caffeine.gwanghwamun.domain.review.service;

import com.caffeine.gwanghwamun.common.exception.CustomException;
import com.caffeine.gwanghwamun.common.exception.ErrorCode;
import com.caffeine.gwanghwamun.domain.order.entity.Order;
import com.caffeine.gwanghwamun.domain.order.entity.OrderStatus;
import com.caffeine.gwanghwamun.domain.order.repository.OrderRepository;
import com.caffeine.gwanghwamun.domain.review.dto.ReviewCreateReqDTO;
import com.caffeine.gwanghwamun.domain.review.dto.ReviewReplyCreateReqDTO;
import com.caffeine.gwanghwamun.domain.review.dto.ReviewResDTO;
import com.caffeine.gwanghwamun.domain.review.entity.Review;
import com.caffeine.gwanghwamun.domain.review.entity.ReviewReply;
import com.caffeine.gwanghwamun.domain.review.repository.ReviewReplyRepository;
import com.caffeine.gwanghwamun.domain.review.repository.ReviewRepository;
import com.caffeine.gwanghwamun.domain.store.entity.Store;
import com.caffeine.gwanghwamun.domain.store.repository.StoreRepository;
import com.caffeine.gwanghwamun.domain.store.service.StoreService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
@Service
public class ReviewService {
	private final ReviewRepository reviewRepository;
	private final ReviewReplyRepository reviewReplyRepository;
	private final StoreRepository storeRepository;
	private final OrderRepository orderRepository;
	private final StoreService storeService;

	@Transactional
	public ReviewResDTO saveReview(UUID orderId, ReviewCreateReqDTO reviewCreateReqDTO, Long userId) {

		Order order =
				orderRepository
						.findById(orderId)
						.orElseThrow((() -> new CustomException(ErrorCode.ORDER_NOT_FOUND)));

		validateSaveReview(order, userId);

		Review review =
				Review.builder()
						.userId(userId)
						.storeId(order.getStore().getStoreId())
						.orderId(orderId)
						.gid(reviewCreateReqDTO.gid())
						.rating(reviewCreateReqDTO.rating())
						.content(reviewCreateReqDTO.content())
						.build();
		Review savedReview = reviewRepository.save(review);
		storeService.updateStoreRating(review.getStoreId());
		return ReviewResDTO.from(savedReview);
	}

	private void validateSaveReview(Order order, Long userId) {
		if (!order.getUser().getUserId().equals(userId)) {
			throw new CustomException(ErrorCode.REVIEW_CREATE_UNAUTHORIZED);
		}

		if (order.getOrderStatus() != OrderStatus.DELIVERY_COMPLETED) {
			throw new CustomException(ErrorCode.REVIEW_CREATE_NOT_ACCEPTED);
		}

		if (reviewRepository.existsByOrderId(order.getOrderId())) {
			throw new CustomException(ErrorCode.REVIEW_DUPLICATED);
		}
	}

	@Transactional
	public ReviewResDTO findReviewById(UUID reviewId) {
		Review existReview =
				reviewRepository
						.findById(reviewId)
						.orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));
		return ReviewResDTO.from(existReview);
	}

	@Transactional
	public void deleteReview(UUID reviewId, Long userId) {
		Review review =
				reviewRepository
						.findById(reviewId)
						.orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));
		if (!review.getUserId().equals(userId)) {
			throw new CustomException(ErrorCode.REVIEW_DELETE_UNAUTHORIZED);
		}
		reviewReplyRepository.deleteByReviewId(reviewId);
		reviewRepository.deleteById(reviewId);
		reviewRepository.flush();
		storeService.updateStoreRating(review.getStoreId());
	}

	@Transactional
	public Page<ReviewResDTO> findReviewListByStore(UUID storeId, Pageable pageable) {
		Page<Review> reviews = reviewRepository.findByStoreId(storeId, pageable);
		return reviews.map(ReviewResDTO::from);
	}

	@Transactional
	public void deleteReply(UUID replyId, Long userId) {
		ReviewReply reply =
				reviewReplyRepository
						.findById(replyId)
						.orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));
		if (!reply.getOwnerId().equals(userId)) {
			throw new CustomException(ErrorCode.REVIEW_REPLY_DELETE_UNAUTHORIZED);
		}
		reviewReplyRepository.deleteById(replyId);
	}

	@Transactional
	public UUID createReply(
			UUID reviewId, ReviewReplyCreateReqDTO reviewReplyCreateReqDTO, Long userId) {
		Review existReview =
				reviewRepository
						.findByIdWithReply(reviewId)
						.orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));

		isOwner(existReview, userId);
		isReplyDuplicated(existReview);

		ReviewReply reply =
				ReviewReply.builder()
						.review(existReview)
						.ownerId(userId)
						.content(reviewReplyCreateReqDTO.content())
						.build();

		ReviewReply newReview = reviewReplyRepository.save(reply);
		return newReview.getReplyId();
	}

	private void isOwner(Review review, Long userId) {
		Store store =
				storeRepository
						.findById(review.getStoreId())
						.orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));
		if (!store.getUser().getUserId().equals(userId)) {
			throw new CustomException(ErrorCode.REVIEW_REPLY_CREATE_UNAUTHORIZED);
		}
	}

	private void isReplyDuplicated(Review review) {
		if (review.getReply() != null) {
			throw new CustomException(ErrorCode.REVIEW_REPLY_DUPLICATED);
		}
	}
}

package com.caffeine.gwanghwamun.domain.review.service;

import com.caffeine.gwanghwamun.common.exception.CustomException;
import com.caffeine.gwanghwamun.common.exception.ErrorCode;
import com.caffeine.gwanghwamun.domain.review.dto.ReviewCreateReqDTO;
import com.caffeine.gwanghwamun.domain.review.dto.ReviewResDTO;
import com.caffeine.gwanghwamun.domain.review.entity.Review;
import com.caffeine.gwanghwamun.domain.review.repository.ReviewRepository;
import com.caffeine.gwanghwamun.domain.user.entity.User;
import com.caffeine.gwanghwamun.domain.user.security.UserDetailsImpl;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
@Service
public class ReviewService {


    private final ReviewRepository reviewRepository;

    @Transactional
    public ReviewResDTO saveReview(UUID storeId, ReviewCreateReqDTO reviewCreateReqDTO, User user) {
        Review review =
                Review.builder()
                        //.userId(user.getUserId())
                        .storeId(storeId)
                        //.orderId()
                        .groupId(1L)
                        .rating(reviewCreateReqDTO.rating())
                        .content(reviewCreateReqDTO.content())
                        .build();
        Review savedReview = this.reviewRepository.save(review);
        return ReviewResDTO.from(savedReview);

    }

    @Transactional
    public ReviewResDTO findReviewById(UUID reviewId) {
        Review existReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));
        return ReviewResDTO.from(existReview);
    }


    @Transactional
    public void deleteReview(UUID reviewId, UserDetailsImpl userDetails) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));
        if(!review.getUserId().equals(userDetails.getUser().getUserId())){
            throw new CustomException(ErrorCode.REVIEW_DELETE_UNAUTHORIZED);
        }
        reviewRepository.deleteById(reviewId);
    }
}

package com.caffeine.gwanghwamun.domain.review.controller;

import com.caffeine.gwanghwamun.common.response.ApiResponse;
import com.caffeine.gwanghwamun.common.response.ResponseUtil;
import com.caffeine.gwanghwamun.common.security.model.UserDetailsImpl;
import com.caffeine.gwanghwamun.common.success.SuccessCode;
import com.caffeine.gwanghwamun.domain.review.dto.ReviewCreateReqDTO;
import com.caffeine.gwanghwamun.domain.review.dto.ReviewReplyCreateReqDTO;
import com.caffeine.gwanghwamun.domain.review.dto.ReviewResDTO;
import com.caffeine.gwanghwamun.domain.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewService reviewService;


	@Operation(summary = "리뷰 생성 API", description = "리뷰를 생성할 수 있다.")
	@PostMapping("/orders/{orderId}/reviews")
	public ResponseEntity<ApiResponse<ReviewResDTO>> createReview(
			@PathVariable("orderId") UUID orderId,
			@Valid @RequestBody ReviewCreateReqDTO reviewCreateReqDTO,
			@AuthenticationPrincipal UserDetailsImpl userDetails) {
		ReviewResDTO reviewResDTO =
				reviewService.saveReview(orderId, reviewCreateReqDTO, userDetails.getUser().getUserId());
		return ResponseUtil.successResponse(SuccessCode.REVIEW_SAVE_SUCCESS, reviewResDTO);
	}

	@Operation(summary = "리뷰 단건 조회 API", description = "리뷰 단건 조회를 할 수 있다.")
	@GetMapping("/reviews/{reviewId}")
	public ResponseEntity<ApiResponse<ReviewResDTO>> getReview(
			@PathVariable("reviewId") UUID reviewId) {
		ReviewResDTO reviewResDTO = reviewService.findReviewById(reviewId);
		return ResponseUtil.successResponse(SuccessCode.REVIEW_FIND_SUCCESS, reviewResDTO);
	}


	@Operation(summary = "가게 리뷰 목록 조회 API", description = "가게의 리뷰들을 조회 할 수 있다.")
	@GetMapping("/stores/{storeId}/reviews")
	public ResponseEntity<ApiResponse<Page<ReviewResDTO>>> getReviewList(
			@PathVariable("storeId") UUID storeId, @PageableDefault(sort = "createAt", direction = Sort.Direction.ASC) Pageable pageable) {
		Page<ReviewResDTO> reviewList = reviewService.findReviewListByStore(storeId, pageable);
		return ResponseUtil.successResponse(SuccessCode.REVIEW_LIST_FIND_SUCCESS, reviewList);
	}

	@Operation(summary = "리뷰 삭제 API", description = "작성자는 리뷰 삭제를 할 수 있다.")
	@DeleteMapping("/reviews/{reviewId}")
	public ResponseEntity<ApiResponse<Void>> deleteReview(
			@PathVariable("reviewId") UUID reviewId,
			@AuthenticationPrincipal UserDetailsImpl userDetails) {
		reviewService.deleteReview(reviewId, userDetails.getUser().getUserId());
		return ResponseUtil.successResponse(SuccessCode.REVIEW_DELETE_SUCCESS);
	}

	@PreAuthorize("hasAnyRole('OWNER','MANAGER','MASTER')")
	@Operation(summary = "사장님 리뷰 답글 작성 API", description = "사장님은 리뷰 답글을 작성할 수 있다.")
	@PostMapping("/reviews/{reviewId}/reply")
	public ResponseEntity<ApiResponse<UUID>> createReply(
			@PathVariable("reviewId") UUID reviewId,
			@Valid @RequestBody ReviewReplyCreateReqDTO reviewReplyCreateReqDTO,
			@AuthenticationPrincipal UserDetailsImpl userDetails) {
		UUID reviewReplyId =
				reviewService.createReply(
						reviewId, reviewReplyCreateReqDTO, userDetails.getUser().getUserId());
		return ResponseUtil.successResponse(SuccessCode.REVIEW_REPLY_SAVE_SUCCESS, reviewReplyId);
	}

	@PreAuthorize("hasAnyRole('OWNER','MANAGER','MASTER')")
	@Operation(summary = "사장님 답글 삭제 API", description = "사장님은 리뷰 답글을 삭제할 수 있다.")
	@DeleteMapping("/reviews/{replyId}")
	public ResponseEntity<ApiResponse<Void>> deleteReply(
			@PathVariable("replyId") UUID replyId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		reviewService.deleteReply(replyId, userDetails.getUser().getUserId());
		return ResponseUtil.successResponse(SuccessCode.REVIEW_REPLY_DELETE_SUCCESS);
	}
}

package com.caffeine.gwanghwamun.domain.review.controller;

import com.caffeine.gwanghwamun.common.response.ApiResponse;
import com.caffeine.gwanghwamun.common.response.ResponseUtil;
import com.caffeine.gwanghwamun.common.success.SuccessCode;
import com.caffeine.gwanghwamun.domain.review.dto.ReviewCreateReqDTO;
import com.caffeine.gwanghwamun.domain.review.dto.ReviewResDTO;
import com.caffeine.gwanghwamun.domain.review.service.ReviewService;
import com.caffeine.gwanghwamun.domain.user.entity.User;
import com.caffeine.gwanghwamun.domain.user.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class ReviewController {

    private ReviewService reviewService;

    @Operation(summary = "리뷰 생성", description = "리뷰를 생성할 수 있다.")
    @PostMapping("/orders/{orderId}/reviews")
    public ResponseEntity<ApiResponse<ReviewResDTO>> createReview(
            @PathVariable("orderId") UUID orderId,
            @RequestBody ReviewCreateReqDTO reviewCreateReqDTO,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ){
        ReviewResDTO reviewResDTO = reviewService.saveReview(orderId, reviewCreateReqDTO, userDetails.getUser());
        return ResponseUtil.successResponse(SuccessCode.REVIEW_SAVE_SUCCESS, reviewResDTO);
    }

    @Operation(summary = "리뷰 단건 조회", description = "리뷰 단건 조회를 할 수 있다.")
    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewResDTO>> getReview(
            @PathVariable("reviewId") UUID reviewId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        ReviewResDTO reviewResDTO = reviewService.findReviewById(reviewId);
        return ResponseUtil.successResponse(SuccessCode.REVIEW_FIND_SUCCESS, reviewResDTO);
    }

    @GetMapping("/reviews/")

    @Operation(summary = "리뷰 삭제", description = "작성자는 리뷰 삭제를 할 수 있다.")
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<ApiResponse<Void>> deleteReview(
            @PathVariable("reviewId") UUID reviewId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        reviewService.deleteReview(reviewId, userDetails);
        return ResponseUtil.successResponse(SuccessCode.REVIEW_DELETE_SUCCESS);
    }



}

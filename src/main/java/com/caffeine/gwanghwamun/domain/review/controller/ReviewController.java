package com.caffeine.gwanghwamun.domain.review.controller;

import com.caffeine.gwanghwamun.common.response.ApiResponse;
import com.caffeine.gwanghwamun.common.response.ResponseUtil;
import com.caffeine.gwanghwamun.common.success.SuccessCode;
import com.caffeine.gwanghwamun.domain.review.service.ReviewService;
import com.caffeine.gwanghwamun.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v1/review")
@RequiredArgsConstructor
public class ReviewController {

    private ReviewService reviewService;

    public ResponseEntity<ApiResponse<ReviewResDTO>> createReview(
            @PathVariable("storeId")UUID storeId,
            @RequestBody ReviewCreateReqDTO reviewCreateReqDTO,
            @AuthenticationPrincipal User user
            ){
        ReviewResDTO reviewResDTO = reviewService.saveReview(storeId, reviewCreateReqDTO);
        return ResponseUtil.successResponse(SuccessCode.REVIEW_SAVE_SUCCESS, reviewResDTO);
    }
}

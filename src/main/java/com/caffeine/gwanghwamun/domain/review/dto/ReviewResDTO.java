package com.caffeine.gwanghwamun.domain.review.dto;

import com.caffeine.gwanghwamun.domain.review.entity.Review;
import java.util.UUID;

public record ReviewResDTO(UUID reviewId, String content, Long rating, ReviewReplyResDTO reply) {

	public static ReviewResDTO from(Review review) {
		return new ReviewResDTO(
				review.getReviewId(),
				review.getContent(),
				review.getRating(),
				review.getReply() != null ? ReviewReplyResDTO.from(review.getReply()) : null);
	}
}

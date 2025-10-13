package com.caffeine.gwanghwamun.domain.review.dto;

import com.caffeine.gwanghwamun.domain.review.entity.ReviewReply;
import java.util.UUID;

public record ReviewReplyResDTO(
        UUID replyId,
        Long ownerId,
        String content
) {
    public static ReviewReplyResDTO from(ReviewReply reply) {
        return new ReviewReplyResDTO(reply.getReplyId(), reply.getOwnerId(), reply.getContent());
    }
}

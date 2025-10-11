package com.caffeine.gwanghwamun.domain.review.entity;

import com.caffeine.gwanghwamun.domain.BaseEntity;
import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "p_review_reply")
public class ReviewReply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "reply_id")
    private UUID replyId;

    @Column(name = "review_id", nullable = false)
    private UUID reviewId;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @Column(name = "content", nullable = false)
    private String content;

    @Builder
    public ReviewReply(UUID reviewId, Long ownerId, String content){
        this.reviewId = reviewId;
        this.ownerId = ownerId;
        this.content = content;
    }

    public void updateContent(String content){
        this.content = content;
    }
}

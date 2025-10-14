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

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "review_id", nullable = false, unique = true)
    private Review review;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @Column(name = "content", nullable = false)
    private String content;

    @Builder
    public ReviewReply(Review review, Long ownerId, String content){
        this.review = review;
        this.ownerId = ownerId;
        this.content = content;
    }

    public void updateContent(String content){
        this.content = content;
    }
}

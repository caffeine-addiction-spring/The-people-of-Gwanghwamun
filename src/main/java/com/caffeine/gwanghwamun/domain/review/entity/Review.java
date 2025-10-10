package com.caffeine.gwanghwamun.domain.review.entity;


import com.caffeine.gwanghwamun.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "p_review")
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "review_id")
    private Long reviewId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "store_id", nullable = false)
    private Long storeId;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "rating", nullable = false)
    private Long rating;

    @Column(name = "content", nullable = false)
    private String content;

    @OneToOne(mappedBy = "reivew", fetch = FetchType.LAZY)
    private ReviewReply reply;

    @Builder
    public Review(Long userId, Long storeId, Long orderId, Long groupId, Long rating, String content) {
        this.userId = userId;
        this.storeId = storeId;
        this.orderId = orderId;
        this.groupId = groupId;
        this.rating = rating;
        this.content = content;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateRating(Long rating) {
        this.rating = rating;
    }

    private void validateRating(Long rating) {
        if (rating == null || rating < 1 || rating > 5) {
            throw new IllegalArgumentException("평점은 1~5 사이의 값이어야 합니다.");
        }    }

}

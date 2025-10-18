package com.caffeine.gwanghwamun.domain.review.repository;

import com.caffeine.gwanghwamun.domain.review.entity.ReviewReply;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewReplyRepository extends JpaRepository<ReviewReply, UUID> {
	@Modifying
	@Query("delete from ReviewReply rr where rr.review.reviewId = :reviewId")
	void deleteByReviewId(@Param("reviewId") UUID reviewId);
}

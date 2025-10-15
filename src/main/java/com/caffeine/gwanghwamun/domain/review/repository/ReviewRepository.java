package com.caffeine.gwanghwamun.domain.review.repository;

import com.caffeine.gwanghwamun.domain.review.entity.Review;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
	@EntityGraph(attributePaths = {"reply"})
	Page<Review> findByStoreId(UUID storeId, Pageable pageable);

	@EntityGraph(attributePaths = {"reply"})
	@Query("select r from Review r where r.reviewId = :id")
	Optional<Review> findByIdWithReply(@Param("id") UUID id);

	boolean existsByOrderId(UUID orderId);
}

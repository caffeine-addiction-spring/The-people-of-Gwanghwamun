package com.caffeine.gwanghwamun.domain.review.repository;

import com.caffeine.gwanghwamun.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
}

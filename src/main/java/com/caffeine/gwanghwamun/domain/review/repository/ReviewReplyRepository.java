package com.caffeine.gwanghwamun.domain.review.repository;

import com.caffeine.gwanghwamun.domain.review.entity.ReviewReply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReviewReplyRepository extends JpaRepository<ReviewReply, UUID> {
}

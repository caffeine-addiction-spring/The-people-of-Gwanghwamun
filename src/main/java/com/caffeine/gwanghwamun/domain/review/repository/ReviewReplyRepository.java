package com.caffeine.gwanghwamun.domain.review.repository;

import com.caffeine.gwanghwamun.domain.review.entity.ReviewReply;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewReplyRepository extends JpaRepository<ReviewReply, UUID> {}

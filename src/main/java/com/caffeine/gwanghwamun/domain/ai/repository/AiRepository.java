package com.caffeine.gwanghwamun.domain.ai.repository;

import com.caffeine.gwanghwamun.domain.ai.entity.Ai;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AiRepository extends JpaRepository<Ai, UUID> {
    Page<Ai> findAllByDeletedAtIsNull(Pageable pageable);
}

package com.caffeine.gwanghwamun.domain.ai.repository;

import com.caffeine.gwanghwamun.domain.ai.entity.Ai;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AiRepository extends JpaRepository<Ai, UUID> {}

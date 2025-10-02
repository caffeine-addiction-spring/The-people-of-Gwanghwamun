package com.caffeine.gwanghwamun.domain.test.repository;

import com.caffeine.gwanghwamun.domain.test.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<TestEntity, Long> {}

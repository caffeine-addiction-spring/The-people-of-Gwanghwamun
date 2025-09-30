package com.caffeine.gwanghwamun.repository;

import com.caffeine.gwanghwamun.domain.ExampleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<ExampleEntity, Long> {
}

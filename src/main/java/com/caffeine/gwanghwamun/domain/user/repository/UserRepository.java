package com.caffeine.gwanghwamun.domain.user.repository;

import com.caffeine.gwanghwamun.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}

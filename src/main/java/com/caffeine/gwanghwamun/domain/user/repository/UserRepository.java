package com.caffeine.gwanghwamun.domain.user.repository;

import com.caffeine.gwanghwamun.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
}

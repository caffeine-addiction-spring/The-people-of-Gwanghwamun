package com.caffeine.gwanghwamun.domain.address.repository;

import com.caffeine.gwanghwamun.domain.address.entity.UserAddress;
import com.caffeine.gwanghwamun.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAddressRepository extends JpaRepository<UserAddress, UUID> {
	List<UserAddress> findAllByUserAndDeletedAtIsNull(User user);

	Optional<UserAddress> findByUserAndIsDefaultTrueAndDeletedAtIsNull(User user);
}

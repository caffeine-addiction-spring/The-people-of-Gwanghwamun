package com.caffeine.gwanghwamun.domain.address.repository;

import com.caffeine.gwanghwamun.domain.address.entity.Address;
import com.caffeine.gwanghwamun.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, UUID> {
	List<Address> findAllByUserAndDeletedAtIsNull(User user);

	Optional<Address> findByUserAndIsDefaultTrueAndDeletedAtIsNull(User user);
}

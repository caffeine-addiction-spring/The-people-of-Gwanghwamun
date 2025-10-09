package com.caffeine.gwanghwamun.domain.address.repository;

import com.caffeine.gwanghwamun.domain.address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {}

package com.caffeine.gwanghwamun.domain.region.repository;

import com.caffeine.gwanghwamun.domain.region.entity.Address;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Address, UUID> {}

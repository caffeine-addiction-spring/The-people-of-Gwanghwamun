package com.caffeine.gwanghwamun.domain.store.repository;

import com.caffeine.gwanghwamun.domain.store.entity.Store;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, UUID> {
	Store save(Store store);
}

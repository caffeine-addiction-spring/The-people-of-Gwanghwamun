package com.caffeine.gwanghwamun.domain.store.repository;

import com.caffeine.gwanghwamun.domain.store.entity.Store;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoreRepository extends JpaRepository<Store, UUID> {
	Store save(Store store);

	@Query("SELECT s FROM Store s WHERE s.deletedAt IS NULL")
	Page<Store> findAllActiveStores(Pageable pageable);

	@Query("SELECT s FROM Store s WHERE s.storeId = :storeId AND s.deletedAt IS NULL")
	Optional<Store> findActiveById(@Param("storeId") UUID storeId);

	@Query(
			"""
		SELECT s FROM Store s
		WHERE s.deletedAt IS NULL
			AND (
					LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
					LOWER(s.address) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
					LOWER(s.storeCategory) LIKE LOWER(CONCAT('%', :keyword, '%'))
			)
		""")
	Page<Store> searchActiveStores(@Param("keyword") String keyword, Pageable pageable);
}

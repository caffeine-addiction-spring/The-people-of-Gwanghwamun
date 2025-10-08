package com.caffeine.gwanghwamun.domain.menu.repository;

import com.caffeine.gwanghwamun.domain.menu.entity.Menu;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MenuRepository extends JpaRepository<Menu, UUID> {
	@Query("SELECT m FROM Menu m WHERE m.menuId = :menuId AND m.deletedAt IS NULL")
	Optional<Menu> findByIdAndNotDeleted(@Param("menuId") UUID menuId);

	@Query("SELECT m FROM Menu m WHERE m.storeId = :storeId AND m.deletedAt IS NULL")
	Page<Menu> findByStoreIdAndNotDeleted(@Param("storeId") UUID storeId, Pageable pageable);

	@Query(
			"SELECT m FROM Menu m WHERE m.storeId = :storeId AND m.deletedAt IS NULL AND m.isHidden = false")
	Page<Menu> findByStoreIdAndNotDeletedAndNotHidden(
			@Param("storeId") UUID storeId, Pageable pageable);
}

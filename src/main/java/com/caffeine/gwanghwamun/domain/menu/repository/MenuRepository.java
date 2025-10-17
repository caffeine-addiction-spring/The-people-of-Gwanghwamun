package com.caffeine.gwanghwamun.domain.menu.repository;

import com.caffeine.gwanghwamun.domain.menu.entity.Menu;
import com.caffeine.gwanghwamun.domain.menu.entity.QMenu;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface MenuRepository extends JpaRepository<Menu, UUID>, QuerydslPredicateExecutor<Menu> {

	default Optional<Menu> findByIdAndNotDeleted(UUID menuId) {
		BooleanBuilder builder = new BooleanBuilder();
		QMenu menu = QMenu.menu;
		return findOne(builder.and(menu.menuId.eq(menuId)).and(menu.deletedAt.isNull()));
	}

	@Query(
			"""
	SELECT m FROM Menu m
	WHERE m.storeId = :storeId
		AND m.deletedAt IS NULL
	ORDER BY m.createAt DESC
""")
	Page<Menu> findByStoreIdAndNotDeleted(@Param("storeId") UUID storeId, Pageable pageable);

	default Page<Menu> findByStoreIdAndNotDeletedAndNotHidden(UUID storeId, Pageable pageable) {
		BooleanBuilder builder = new BooleanBuilder();
		QMenu menu = QMenu.menu;
		builder.and(menu.storeId.eq(storeId)).and(menu.deletedAt.isNull()).and(menu.isHidden.eq(false));
		return findAll(builder, pageable);
	}

	@Query(
			"""
		SELECT m FROM Menu m
		WHERE m.deletedAt IS NULL
			AND (
					LOWER(m.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
					LOWER(m.menuContent) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
					LOWER(m.menuCategory) LIKE LOWER(CONCAT('%', :keyword, '%'))
			)
		""")
	Page<Menu> searchAllMenus(@Param("keyword") String keyword, Pageable pageable);
}

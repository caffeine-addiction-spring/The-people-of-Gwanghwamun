package com.caffeine.gwanghwamun.domain.menu.repository;

import com.caffeine.gwanghwamun.domain.menu.entity.MenuOption;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface MenuOptionRepository extends JpaRepository<MenuOption, UUID> {

	@Query("SELECT o FROM MenuOption o WHERE o.menuOptionId = :optionId AND o.deletedAt IS NULL")
	Optional<MenuOption> findByIdAndNotDeleted(@Param("optionId") UUID optionId);

	@Query("SELECT o FROM MenuOption o WHERE o.menuId = :menuId AND o.deletedAt IS NULL AND o.isHidden = false")
	Page<MenuOption> findPublicListByMenuId(@Param("menuId") UUID menuId, Pageable pageable);

	@Query("""
        SELECT o FROM MenuOption o 
        WHERE o.menuId = :menuId 
        AND o.deletedAt IS NULL
        AND (:includeHidden IS NULL OR o.isHidden = :includeHidden)
        AND (:soldOut IS NULL OR o.isSoldOut = :soldOut)
        AND (:optionName IS NULL OR LOWER(o.optionName) LIKE LOWER(CONCAT('%', :optionName, '%')))
        """)
	Page<MenuOption> searchOptions(
			@Param("menuId") UUID menuId,
			@Param("includeHidden") Boolean includeHidden,
			@Param("soldOut") Boolean soldOut,
			@Param("optionName") String optionName,
			Pageable pageable
	);
}

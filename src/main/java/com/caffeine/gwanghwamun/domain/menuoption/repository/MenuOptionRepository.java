package com.caffeine.gwanghwamun.domain.menuoption.repository;

import com.caffeine.gwanghwamun.domain.menuoption.entity.MenuOption;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MenuOptionRepository
		extends JpaRepository<MenuOption, UUID>, MenuOptionQueryRepository {

	@Query("SELECT o FROM MenuOption o WHERE o.menuOptionId = :optionId AND o.deletedAt IS NULL")
	Optional<MenuOption> findByIdAndNotDeleted(@Param("optionId") UUID optionId);

	@Query(
			"SELECT o FROM MenuOption o WHERE o.menuId = :menuId AND o.deletedAt IS NULL AND o.isHidden = false")
	Page<MenuOption> findPublicListByMenuId(@Param("menuId") UUID menuId, Pageable pageable);
}

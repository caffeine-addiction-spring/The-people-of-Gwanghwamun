package com.caffeine.gwanghwamun.domain.menu.repository;

import com.caffeine.gwanghwamun.domain.menu.entity.MenuOption;
import com.caffeine.gwanghwamun.domain.menu.entity.QMenuOption;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.UUID;

public interface MenuOptionRepository extends JpaRepository<MenuOption, UUID>, QuerydslPredicateExecutor<MenuOption> {

	default Optional<MenuOption> findByIdAndNotDeleted(UUID optionId) {
		BooleanBuilder builder = new BooleanBuilder();
		QMenuOption menuOption = QMenuOption.menuOption;
		builder.and(menuOption.menuOptionId.eq(optionId)).and(menuOption.deletedAt.isNull());
		return findOne(builder);
	}

	default Page<MenuOption> findPublicListByMenuId(UUID menuId, Pageable pageable) {
		BooleanBuilder builder = new BooleanBuilder();
		QMenuOption menuOption = QMenuOption.menuOption;
		builder.and(menuOption.menuOptionId.eq(menuId)).and(menuOption.deletedAt.isNull()).and(menuOption.isHidden.eq(false));
		return findAll(builder, pageable);
	}

	default Page<MenuOption> searchOptions(
			UUID menuId,
			Boolean includeHidden,
			Boolean soldOut,
			String optionName,
			Pageable pageable) {
		BooleanBuilder andBuilder = new BooleanBuilder();
		QMenuOption menuOption = QMenuOption.menuOption;
		andBuilder.and(menuOption.menuId.eq(menuId))
				.and(menuOption.isSoldOut.eq(soldOut != null && soldOut))
				.and(menuOption.deletedAt.isNull());
		if (StringUtils.hasText(optionName)) {
			andBuilder.and(menuOption.optionName.containsIgnoreCase(optionName));
		}

		if (includeHidden != null && includeHidden) andBuilder.and(menuOption.isHidden.eq(true));

		return findAll(andBuilder, pageable);
	}
}

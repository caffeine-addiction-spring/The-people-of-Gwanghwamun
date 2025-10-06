package com.caffeine.gwanghwamun.domain.menuoption.repository;

import com.caffeine.gwanghwamun.domain.menuoption.entity.MenuOption;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.UUID;

import static com.caffeine.gwanghwamun.domain.menuoption.entity.QMenuOption.menuOption;

@RequiredArgsConstructor
public class MenuOptionQueryRepositoryImpl implements MenuOptionQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<MenuOption> searchOptions(UUID menuId, Boolean includeHidden, Boolean soldOut, String optionName, Pageable pageable) {
        BooleanBuilder where = new BooleanBuilder();
        where.and(menuOption.deletedAt.isNull());
        where.and(menuOption.menuId.eq(menuId));

        if (Boolean.TRUE.equals(soldOut)) {
            where.and(menuOption.isSoldOut.isTrue());
        } else if (Boolean.FALSE.equals(soldOut)) {
            where.and(menuOption.isSoldOut.isFalse());
        }

        if (!Boolean.TRUE.equals(includeHidden)) {
            where.and(menuOption.isHidden.isFalse());
        }

        if (optionName != null && !optionName.isBlank()) {
            where.and(menuOption.optionName.contains(optionName));
        }

        var content = query
                .selectFrom(menuOption)
                .where(where)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(menuOption.menuOptionId.desc())
                .fetch();

        var countQuery = query
                .select(menuOption.count())
                .from(menuOption)
                .where(where);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}
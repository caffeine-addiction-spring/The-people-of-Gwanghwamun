package com.caffeine.gwanghwamun.domain.menu.repository;

import com.caffeine.gwanghwamun.domain.menu.entity.MenuOption;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MenuOptionQueryRepository {

	Page<MenuOption> searchOptions(
			UUID menuId, Boolean includeHidden, Boolean soldOut, String optionName, Pageable pageable);
}

package com.caffeine.gwanghwamun.domain.menu.service;

import com.caffeine.gwanghwamun.common.exception.CustomException;
import com.caffeine.gwanghwamun.common.exception.ErrorCode;
import com.caffeine.gwanghwamun.domain.menu.dto.request.MenuCreateReqDTO;
import com.caffeine.gwanghwamun.domain.menu.dto.request.MenuUpdateReqDTO;
import com.caffeine.gwanghwamun.domain.menu.dto.response.MenuResDTO;
import com.caffeine.gwanghwamun.domain.menu.entity.Menu;
import com.caffeine.gwanghwamun.domain.menu.repository.MenuRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MenuService {

	private final MenuRepository menuRepository;

	@Transactional
	public MenuResDTO saveMenu(UUID storeId, MenuCreateReqDTO menuCreateReqDTO) {
		Menu menu =
				Menu.builder()
						.storeId(storeId)
						.groupId(1L)
						.menuCategory(menuCreateReqDTO.menuCategory())
						.name(menuCreateReqDTO.name())
						.menuContent(menuCreateReqDTO.content())
						.price(menuCreateReqDTO.price())
						.isSoldOut(false)
						.isHidden(menuCreateReqDTO.isHidden() != null ? menuCreateReqDTO.isHidden() : false)
						.build();

		return new MenuResDTO(menuRepository.save(menu));
	}

	public MenuResDTO findMenuById(UUID storeId, UUID menuId) {
		Menu menu =
				menuRepository
						.findByIdAndNotDeleted(menuId)
						.orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));
		if (!menu.getStoreId().equals(storeId)) {
			throw new CustomException(ErrorCode.MENU_STORE_MISMATCH);
		}
		return new MenuResDTO(menu);
	}

	public Page<MenuResDTO> findMenuListByStore(UUID storeId, Pageable pageable) {
		Page<Menu> page = menuRepository.findByStoreIdAndNotDeleted(storeId, pageable);
		return page.map(MenuResDTO::new);
	}

	@Transactional
	public MenuResDTO updateMenu(UUID storeId, UUID menuId, MenuUpdateReqDTO req) {
		Menu menu =
				menuRepository
						.findByIdAndNotDeleted(menuId)
						.orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));
		if (!menu.getStoreId().equals(storeId)) {
			throw new CustomException(ErrorCode.MENU_STORE_MISMATCH);
		}
		menu.updateMenu(req.name(), req.content(), req.price(), req.isSoldOut(), req.isHidden());
		return new MenuResDTO(menu);
	}

	@Transactional
	public void deleteMenu(UUID storeId, UUID menuId) {
		Menu menu =
				menuRepository
						.findByIdAndNotDeleted(menuId)
						.orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));
		if (!menu.getStoreId().equals(storeId)) {
			throw new CustomException(ErrorCode.MENU_STORE_MISMATCH);
		}
		menu.softDelete();
	}

	@Transactional
	public MenuResDTO updateMenuVisibility(UUID storeId, UUID menuId, Boolean hidden) {
		Menu menu =
				menuRepository
						.findByIdAndNotDeleted(menuId)
						.orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));
		if (!menu.getStoreId().equals(storeId)) {
			throw new CustomException(ErrorCode.MENU_STORE_MISMATCH);
		}
		if (Boolean.TRUE.equals(hidden)) {
			menu.hideMenu();
		} else {
			menu.showMenu();
		}
		return new MenuResDTO(menu);
	}

	@Transactional
	public MenuResDTO updateMenuSoldOut(UUID storeId, UUID menuId, Boolean isSoldOut) {
		Menu menu =
				menuRepository
						.findByIdAndNotDeleted(menuId)
						.orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));
		if (!menu.getStoreId().equals(storeId)) {
			throw new CustomException(ErrorCode.MENU_STORE_MISMATCH);
		}
		if (Boolean.TRUE.equals(isSoldOut)) {
			menu.markAsSoldOut();
		} else {
			menu.markAsAvailable();
		}
		return new MenuResDTO(menu);
	}
}

package com.caffeine.gwanghwamun.domain.menu.service;

import com.caffeine.gwanghwamun.common.exception.CustomException;
import com.caffeine.gwanghwamun.common.exception.ErrorCode;
import com.caffeine.gwanghwamun.common.security.model.UserDetailsImpl;
import com.caffeine.gwanghwamun.domain.ai.service.AiService;
import com.caffeine.gwanghwamun.domain.file.dto.FileInfoResDTO;
import com.caffeine.gwanghwamun.domain.file.entity.FileStatus;
import com.caffeine.gwanghwamun.domain.file.service.FileService;
import com.caffeine.gwanghwamun.domain.menu.dto.request.MenuCreateReqDTO;
import com.caffeine.gwanghwamun.domain.menu.dto.request.MenuUpdateReqDTO;
import com.caffeine.gwanghwamun.domain.menu.dto.response.MenuResDTO;
import com.caffeine.gwanghwamun.domain.menu.entity.Menu;
import com.caffeine.gwanghwamun.domain.menu.repository.MenuRepository;
import com.caffeine.gwanghwamun.domain.store.entity.Store;
import com.caffeine.gwanghwamun.domain.store.repository.StoreRepository;
import com.caffeine.gwanghwamun.domain.user.entity.User;
import com.caffeine.gwanghwamun.domain.user.entity.UserRoleEnum;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MenuService {

	private final MenuRepository menuRepository;
	private final StoreRepository storeRepository;
	private final AiService aiService;
	private final FileService fileService;

	private User requireAuthenticatedUser(UserDetailsImpl principal) {
		if (principal == null || principal.getUser() == null) {
			throw new CustomException(ErrorCode.UNAUTHORIZED);
		}
		return principal.getUser();
	}

	private void validateStoreOwnership(UUID storeId, UserDetailsImpl principal) {
		User user = requireAuthenticatedUser(principal);

		Store store =
				storeRepository
						.findActiveById(storeId)
						.orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));

		if (user.getRole() == UserRoleEnum.OWNER
				&& !store.getUser().getUserId().equals(user.getUserId())) {
			throw new CustomException(ErrorCode.FORBIDDEN);
		}
	}

	@Transactional
	public MenuResDTO saveMenu(UUID storeId, MenuCreateReqDTO req, UserDetailsImpl principal) {
		validateStoreOwnership(storeId, principal);

		String content = req.content();
		if (Boolean.TRUE.equals(req.useAI()) && req.aiPrompt() != null) {
			content = aiService.ask(req.aiPrompt());
		}

		Menu menu =
				Menu.builder()
						.storeId(storeId)
						.groupId(req.groupId())
						.menuCategory(req.menuCategory())
						.name(req.name())
						.menuContent(content)
						.price(req.price())
						.isSoldOut(false)
						.isHidden(req.isHidden() != null ? req.isHidden() : false)
						.build();

		return new MenuResDTO(menuRepository.save(menu));
	}

	public MenuResDTO findMenuById(UUID storeId, UUID menuId, UserDetailsImpl principal) {
		validateStoreOwnership(storeId, principal);

		Menu menu =
				menuRepository
						.findByIdAndNotDeleted(menuId)
						.orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));
		if (!menu.getStoreId().equals(storeId)) {
			throw new CustomException(ErrorCode.MENU_STORE_MISMATCH);
		}

		List<FileInfoResDTO> images = fileService.getList(menu.getGroupId(), "menu", FileStatus.DONE);
		return new MenuResDTO(menu, images);
	}

	public Page<MenuResDTO> findMenuListByStore(
			UUID storeId, Pageable pageable, UserDetailsImpl principal) {
		validateStoreOwnership(storeId, principal);

		Page<Menu> page = menuRepository.findByStoreIdAndNotDeleted(storeId, pageable);
		return page.map(
				menu -> {
					List<FileInfoResDTO> images =
							fileService.getList(menu.getGroupId(), "menu", FileStatus.DONE);
					return new MenuResDTO(menu, images);
				});
	}

	@Transactional
	public MenuResDTO updateMenu(
			UUID storeId, UUID menuId, MenuUpdateReqDTO req, UserDetailsImpl principal) {
		validateStoreOwnership(storeId, principal);

		Menu menu =
				menuRepository
						.findByIdAndNotDeleted(menuId)
						.orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));
		if (!menu.getStoreId().equals(storeId)) {
			throw new CustomException(ErrorCode.MENU_STORE_MISMATCH);
		}
		menu.updateMenu(req.name(), req.content(), req.price(), req.isSoldOut(), req.isHidden());

		List<FileInfoResDTO> images = fileService.getList(menu.getGroupId(), "menu", FileStatus.DONE);
		return new MenuResDTO(menu, images);
	}

	@Transactional
	public void deleteMenu(UUID storeId, UUID menuId, UserDetailsImpl principal) {
		validateStoreOwnership(storeId, principal);

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
	public MenuResDTO updateMenuVisibility(
			UUID storeId, UUID menuId, Boolean hidden, UserDetailsImpl principal) {
		validateStoreOwnership(storeId, principal);

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

		List<FileInfoResDTO> images = fileService.getList(menu.getGroupId(), "menu", FileStatus.DONE);
		return new MenuResDTO(menu, images);
	}

	@Transactional
	public MenuResDTO updateMenuSoldOut(
			UUID storeId, UUID menuId, Boolean isSoldOut, UserDetailsImpl principal) {
		validateStoreOwnership(storeId, principal);

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

		List<FileInfoResDTO> images = fileService.getList(menu.getGroupId(), "menu", FileStatus.DONE);
		return new MenuResDTO(menu, images);
	}

	@Transactional(readOnly = true)
	public Page<MenuResDTO> searchMenus(
			String keyword, int page, int size, String sortBy, String direction) {
		if (size != 10 && size != 30 && size != 50) {
			size = 10;
		}

		Sort.Direction sortDirection =
				direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
		Sort sort = Sort.by(sortDirection, sortBy);
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Menu> menuPage = menuRepository.searchAllMenus(keyword, pageable);
		return menuPage.map(
				menu -> {
					List<FileInfoResDTO> images =
							fileService.getList(menu.getGroupId(), "menu", FileStatus.DONE);
					return new MenuResDTO(menu, images);
				});
	}

	@Transactional(readOnly = true)
	public Page<MenuResDTO> searchMenusByStore(
			UUID storeId, String keyword, int page, int size, String sortBy, String direction, UserDetailsImpl principal) {
		validateStoreOwnership(storeId, principal);

		if (size != 10 && size != 30 && size != 50) {
			size = 10;
		}

		Sort.Direction sortDirection =
				direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
		Sort sort = Sort.by(sortDirection, sortBy);
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Menu> menuPage = menuRepository.searchMenusByStore(storeId, keyword, pageable);
		return menuPage.map(
				menu -> {
					List<FileInfoResDTO> images =
							fileService.getList(menu.getGroupId(), "menu", FileStatus.DONE);
					return new MenuResDTO(menu, images);
				});
	}

}

package com.caffeine.gwanghwamun.domain.menu.service;

import com.caffeine.gwanghwamun.common.exception.CustomException;
import com.caffeine.gwanghwamun.common.exception.ErrorCode;
import com.caffeine.gwanghwamun.common.security.model.UserDetailsImpl;
import com.caffeine.gwanghwamun.domain.menu.dto.request.MenuOptionCreateReqDTO;
import com.caffeine.gwanghwamun.domain.menu.dto.request.MenuOptionSoldOutReqDTO;
import com.caffeine.gwanghwamun.domain.menu.dto.request.MenuOptionUpdateReqDTO;
import com.caffeine.gwanghwamun.domain.menu.dto.response.MenuOptionResDTO;
import com.caffeine.gwanghwamun.domain.menu.entity.Menu;
import com.caffeine.gwanghwamun.domain.menu.entity.MenuOption;
import com.caffeine.gwanghwamun.domain.menu.repository.MenuOptionRepository;
import com.caffeine.gwanghwamun.domain.menu.repository.MenuRepository;
import com.caffeine.gwanghwamun.domain.store.entity.Store;
import com.caffeine.gwanghwamun.domain.store.repository.StoreRepository;
import com.caffeine.gwanghwamun.domain.user.entity.User;
import com.caffeine.gwanghwamun.domain.user.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MenuOptionService {

	private final MenuOptionRepository menuOptionRepository;
	private final MenuRepository menuRepository;
	private final StoreRepository storeRepository;

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

	private void validateMenuBelongsToStore(UUID storeId, UUID menuId) {
		Menu menu =
				menuRepository
						.findByIdAndNotDeleted(menuId)
						.orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));
		if (!menu.getStoreId().equals(storeId)) {
			throw new CustomException(ErrorCode.MENU_STORE_MISMATCH);
		}
	}

	@Transactional
	public MenuOptionResDTO saveOption(
			UUID storeId, UUID menuId, MenuOptionCreateReqDTO req, UserDetailsImpl principal) {
		validateStoreOwnership(storeId, principal);
		validateMenuBelongsToStore(storeId, menuId);

		MenuOption option =
				MenuOption.builder()
						.menuId(menuId)
						.optionName(req.optionName())
						.price(req.price())
						.content(req.content())
						.isHidden(req.isHidden())
						.isSoldOut(req.isSoldOut())
						.build();

		return new MenuOptionResDTO(menuOptionRepository.save(option));
	}

	public Page<MenuOptionResDTO> findOptionList(
			UUID storeId,
			UUID menuId,
			Boolean includeHidden,
			Boolean soldOut,
			String optionName,
			Pageable pageable,
			UserDetailsImpl principal) {
		validateStoreOwnership(storeId, principal);
		validateMenuBelongsToStore(storeId, menuId);

		Page<MenuOption> page =
				menuOptionRepository.searchOptions(menuId, includeHidden, soldOut, optionName, pageable);
		return page.map(MenuOptionResDTO::new);
	}

	public MenuOptionResDTO findOption(
			UUID storeId, UUID menuId, UUID optionId, UserDetailsImpl principal) {
		validateStoreOwnership(storeId, principal);
		validateMenuBelongsToStore(storeId, menuId);

		MenuOption option =
				menuOptionRepository
						.findByIdAndNotDeleted(optionId)
						.orElseThrow(() -> new CustomException(ErrorCode.MENU_OPTION_NOT_FOUND));
		if (!option.getMenuId().equals(menuId)) {
			throw new CustomException(ErrorCode.MENU_STORE_MISMATCH);
		}
		return new MenuOptionResDTO(option);
	}

	@Transactional
	public MenuOptionResDTO updateOption(
			UUID storeId,
			UUID menuId,
			UUID optionId,
			MenuOptionUpdateReqDTO req,
			UserDetailsImpl principal) {
		validateStoreOwnership(storeId, principal);
		validateMenuBelongsToStore(storeId, menuId);

		MenuOption option =
				menuOptionRepository
						.findByIdAndNotDeleted(optionId)
						.orElseThrow(() -> new CustomException(ErrorCode.MENU_OPTION_NOT_FOUND));

		if (!option.getMenuId().equals(menuId)) {
			throw new CustomException(ErrorCode.MENU_STORE_MISMATCH);
		}

		option.update(req.optionName(), req.price(), req.content(), req.isHidden(), req.isSoldOut());
		return new MenuOptionResDTO(option);
	}

	@Transactional
	public void deleteOption(
			UUID storeId, UUID menuId, UUID optionId, String deleter, UserDetailsImpl principal) {
		validateStoreOwnership(storeId, principal);
		validateMenuBelongsToStore(storeId, menuId);

		MenuOption option =
				menuOptionRepository
						.findByIdAndNotDeleted(optionId)
						.orElseThrow(() -> new CustomException(ErrorCode.MENU_OPTION_NOT_FOUND));
		if (!option.getMenuId().equals(menuId)) {
			throw new CustomException(ErrorCode.MENU_STORE_MISMATCH);
		}
		option.softDelete(deleter);
	}

	@Transactional
	public MenuOptionResDTO updateOptionVisibility(
			UUID storeId, UUID menuId, UUID optionId, Boolean hidden, UserDetailsImpl principal) {
		validateStoreOwnership(storeId, principal);
		validateMenuBelongsToStore(storeId, menuId);

		MenuOption option =
				menuOptionRepository
						.findByIdAndNotDeleted(optionId)
						.orElseThrow(() -> new CustomException(ErrorCode.MENU_OPTION_NOT_FOUND));
		if (!option.getMenuId().equals(menuId)) {
			throw new CustomException(ErrorCode.MENU_STORE_MISMATCH);
		}

		if (Boolean.TRUE.equals(hidden)) option.hideOption();
		else option.showOption();

		return new MenuOptionResDTO(option);
	}

	@Transactional
	public MenuOptionResDTO updateSoldOut(
			UUID storeId,
			UUID menuId,
			UUID optionId,
			MenuOptionSoldOutReqDTO req,
			UserDetailsImpl principal) {
		validateStoreOwnership(storeId, principal);
		validateMenuBelongsToStore(storeId, menuId);

		MenuOption option =
				menuOptionRepository
						.findByIdAndNotDeleted(optionId)
						.orElseThrow(() -> new CustomException(ErrorCode.MENU_OPTION_NOT_FOUND));
		if (!option.getMenuId().equals(menuId)) {
			throw new CustomException(ErrorCode.MENU_STORE_MISMATCH);
		}
		if (Boolean.TRUE.equals(req.isSoldOut())) option.markSoldOut();
		else option.markAvailable();

		return new MenuOptionResDTO(option);
	}
}

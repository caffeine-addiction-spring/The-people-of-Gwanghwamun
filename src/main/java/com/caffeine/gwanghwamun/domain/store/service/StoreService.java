package com.caffeine.gwanghwamun.domain.store.service;

import com.caffeine.gwanghwamun.common.exception.CustomException;
import com.caffeine.gwanghwamun.common.exception.ErrorCode;
import com.caffeine.gwanghwamun.domain.menu.dto.response.MenuResDTO;
import com.caffeine.gwanghwamun.domain.menu.repository.MenuRepository;
import com.caffeine.gwanghwamun.domain.store.dto.request.StoreCreateReqDTO;
import com.caffeine.gwanghwamun.domain.store.dto.request.StoreUpdateReqDTO;
import com.caffeine.gwanghwamun.domain.store.dto.response.StoreCreateResDTO;
import com.caffeine.gwanghwamun.domain.store.dto.response.StoreDetailResDTO;
import com.caffeine.gwanghwamun.domain.store.dto.response.StoreListResDTO;
import com.caffeine.gwanghwamun.domain.store.dto.response.StoreUpdateResDTO;
import com.caffeine.gwanghwamun.domain.store.entity.Store;
import com.caffeine.gwanghwamun.domain.store.repository.StoreRepository;
import com.caffeine.gwanghwamun.domain.user.entity.User;
import com.caffeine.gwanghwamun.domain.user.entity.UserRoleEnum;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoreService {

	private final StoreRepository storeRepository;
	private final MenuRepository menuRepository;

	public StoreCreateResDTO createStore(StoreCreateReqDTO req, User user) {
		Store store = new Store();
		store.setName(req.getName());
		store.setAddress(req.getAddress());
		store.setPhone(req.getPhone());
		store.setStoreCategory(req.getStoreCategory());
		store.setContent(req.getContent());
		store.setMinDeliveryPrice(req.getMinDeliveryPrice() != null ? req.getMinDeliveryPrice() : 0);
		store.setDeliveryTip(req.getDeliveryTip() != null ? req.getDeliveryTip() : 0);
		store.setOperationHours(req.getOperationHours());
		store.setClosedDays(req.getClosedDays());

        store.setUser(user);
		store.setRating(BigDecimal.ZERO);
		store.setReviewCount(0);

		storeRepository.save(store);

		return new StoreCreateResDTO(store.getStoreId(), store.getName(), store.getStoreCategory());
	}

	public Page<StoreListResDTO> getStoreList(int page, int size, String sortBy, String direction) {
		Sort sort =
				direction.equalsIgnoreCase("asc")
						? Sort.by(sortBy).ascending()
						: Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(page, size, sort);
		Page<Store> storePage = storeRepository.findAll(pageable);

		List<StoreListResDTO> dtoList =
				storePage.getContent().stream()
						.map(
								store ->
										new StoreListResDTO(
												store.getStoreId(),
												store.getName(),
												store.getStoreCategory(),
												store.getAddress()))
						.toList();

		return new PageImpl<>(dtoList, pageable, storePage.getTotalElements());
	}

	@Transactional(readOnly = true)
	public StoreDetailResDTO getStoreDetail(UUID storeId, String sort, String order) {
		Store store =
				storeRepository
						.findById(storeId)
						.orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));

		Sort.Direction direction =
				"desc".equalsIgnoreCase(order) ? Sort.Direction.DESC : Sort.Direction.ASC;

		Sort sortOption =
				switch (sort) {
					case "price" -> Sort.by(direction, "price");
					case "name" -> Sort.by(direction, "name");
					default -> Sort.by(Sort.Direction.ASC, "name");
				};

		Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, sortOption);

		List<MenuResDTO> menus =
				menuRepository
						.findByStoreIdAndNotDeletedAndNotHidden(storeId, pageable)
						.getContent()
						.stream()
						.filter(menu -> !menu.getIsSoldOut())
						.map(MenuResDTO::new)
						.toList();

		return new StoreDetailResDTO(store, menus);
	}

	@Transactional
	public StoreUpdateResDTO updateStore(UUID storeId, StoreUpdateReqDTO req, User user) {
		Store store =
				storeRepository
						.findById(storeId)
						.orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));

        if (user.getRole() == UserRoleEnum.OWNER && !store.getUser().getUserId().equals(user.getUserId())) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

		if (req.getName() != null) store.setName(req.getName());
		if (req.getAddress() != null) store.setAddress(req.getAddress());
		if (req.getPhone() != null) store.setPhone(req.getPhone());
		if (req.getStoreCategory() != null) store.setStoreCategory(req.getStoreCategory());
		if (req.getContent() != null) store.setContent(req.getContent());
		if (req.getMinDeliveryPrice() != null) store.setMinDeliveryPrice(req.getMinDeliveryPrice());
		if (req.getDeliveryTip() != null) store.setDeliveryTip(req.getDeliveryTip());
		if (req.getOperationHours() != null) store.setOperationHours(req.getOperationHours());
		if (req.getClosedDays() != null) store.setClosedDays(req.getClosedDays());

		storeRepository.save(store);

		return new StoreUpdateResDTO(
				store.getStoreId(),
				store.getName(),
				store.getStoreCategory(),
				store.getAddress(),
				store.getPhone());
	}

	@Transactional
	public void deleteStore(UUID storeId, User user) {
		Store store =
				storeRepository
						.findById(storeId)
						.orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));

        if (user.getRole() == UserRoleEnum.OWNER && !store.getUser().getUserId().equals(user.getUserId())) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

		if (store.getDeletedAt() != null) {
			throw new CustomException(ErrorCode.ALREADY_DELETED);
		}

		store.setDeletedAt(LocalDateTime.now());
		storeRepository.save(store);
	}
}

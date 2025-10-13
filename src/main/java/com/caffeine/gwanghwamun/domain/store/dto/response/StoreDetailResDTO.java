package com.caffeine.gwanghwamun.domain.store.dto.response;

import com.caffeine.gwanghwamun.domain.menu.dto.response.MenuResDTO;
import com.caffeine.gwanghwamun.domain.store.entity.Store;
import com.caffeine.gwanghwamun.domain.store.entity.StoreCategoryEnum;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.Getter;

@Getter
public class StoreDetailResDTO {

	private final UUID storeId;
	private final String storeName;
	private final StoreCategoryEnum category;
	private final String address;
	private final String phone;
	private final String content;
	private final Integer minDeliveryPrice;
	private final Integer deliveryTip;
	private final String operationHours;
	private final String closedDays;
	private final BigDecimal rating;
	private final Integer reviewCount;
	private final List<MenuResDTO> menus;

	public StoreDetailResDTO(Store store, List<MenuResDTO> menus) {
		this.storeId = store.getStoreId();
		this.storeName = store.getName();
		this.category = store.getStoreCategory();
		this.address = store.getAddress();
		this.phone = store.getPhone();
		this.content = store.getContent();
		this.minDeliveryPrice = store.getMinDeliveryPrice();
		this.deliveryTip = store.getDeliveryTip();
		this.operationHours = store.getOperationHours();
		this.closedDays = store.getClosedDays();
		this.rating = store.getRating();
		this.reviewCount = store.getReviewCount();
		this.menus = menus;
	}
}

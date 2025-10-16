package com.caffeine.gwanghwamun.domain.store.dto.response;

import com.caffeine.gwanghwamun.domain.file.dto.FileInfoResDTO;
import com.caffeine.gwanghwamun.domain.menu.dto.response.MenuResDTO;
import com.caffeine.gwanghwamun.domain.store.entity.Store;
import com.caffeine.gwanghwamun.domain.store.entity.StoreCategoryEnum;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StoreDetailResDTO {

	private UUID storeId;
	private String storeName;
	private StoreCategoryEnum category;
	private String address;
	private String phone;
	private String content;
	private Integer minDeliveryPrice;
	private Integer deliveryTip;
	private String operationHours;
	private String closedDays;
	private BigDecimal rating;
	private Integer reviewCount;
	private String gid;
	private List<MenuResDTO> menus;
	private List<FileInfoResDTO> images;

	public StoreDetailResDTO(Store store, List<MenuResDTO> menus, List<FileInfoResDTO> images) {
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
		this.gid = store.getGid();
		this.menus = menus;
		this.images = images;
	}
}

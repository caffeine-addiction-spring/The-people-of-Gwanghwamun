package com.caffeine.gwanghwamun.domain.store.dto.response;

import com.caffeine.gwanghwamun.domain.store.entity.StoreCategoryEnum;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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
	private Double rating;
	private Integer reviewCount;
}

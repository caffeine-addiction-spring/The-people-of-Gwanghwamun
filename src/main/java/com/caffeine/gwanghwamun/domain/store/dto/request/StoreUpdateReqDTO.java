package com.caffeine.gwanghwamun.domain.store.dto.request;

import com.caffeine.gwanghwamun.domain.store.entity.StoreCategoryEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreUpdateReqDTO {
	private String name;
	private String address;
	private String phone;
	private StoreCategoryEnum storeCategory;
	private String content;
	private Integer minDeliveryPrice;
	private Integer deliveryTip;
	private String operationHours;
	private String closedDays;
}

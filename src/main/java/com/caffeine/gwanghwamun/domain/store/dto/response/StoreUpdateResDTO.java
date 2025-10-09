package com.caffeine.gwanghwamun.domain.store.dto.response;

import com.caffeine.gwanghwamun.domain.store.entity.StoreCategoryEnum;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StoreUpdateResDTO {
	private UUID storeId;
	private String storeName;
	private StoreCategoryEnum category;
	private String address;
	private String phone;
}

package com.caffeine.gwanghwamun.domain.store.dto.response;

import com.caffeine.gwanghwamun.domain.store.entity.StoreCategoryEnum;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StoreCreateResDTO {
	private UUID storeId;
	private String storeName;
	private StoreCategoryEnum storeCategory;
}

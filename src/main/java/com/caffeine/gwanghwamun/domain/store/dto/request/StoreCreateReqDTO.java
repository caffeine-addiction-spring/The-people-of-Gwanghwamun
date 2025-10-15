package com.caffeine.gwanghwamun.domain.store.dto.request;

import com.caffeine.gwanghwamun.domain.store.entity.StoreCategoryEnum;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreCreateReqDTO {

	@NotBlank(message = "가게 이름은 필수 입력 항목입니다.")
	@Size(max = 50, message = "가게 이름은 50자 이하로 입력해주세요.")
	private String name;

	@NotBlank(message = "주소는 필수 입력 항목입니다.")
	private String address;

	@NotBlank(message = "전화번호는 필수 입력 항목입니다.")
	@Pattern(regexp = "^(0\\d{1,2})-?\\d{3,4}-?\\d{4}$", message = "전화번호 형식이 올바르지 않습니다.")
	private String phone;

	@NotNull(message = "카테고리는 필수 선택 항목입니다.")
	private StoreCategoryEnum storeCategory;

	private String content;
	private Integer minDeliveryPrice;
	private Integer deliveryTip;
	private String operationHours;
	private String closedDays;

	@NotNull private String gid;

	// MANAGER/MASTER
	private Long ownerUserId;
}

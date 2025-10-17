package com.caffeine.gwanghwamun.domain.address.dto.request;

import jakarta.validation.constraints.Pattern;
import org.springframework.lang.Nullable;

public record UpdateUserAddressReqDTO(
		@Nullable String label,
		@Nullable String recipient,
		@Nullable @Pattern(regexp = "^010\\d{8}$", message = "유효한 휴대폰 번호 형식(010으로 시작, 11자리 숫자)이 아닙니다.")
				String phone,
		@Nullable String address,
		@Nullable @Pattern(regexp = "^\\d{5}$", message = "유효한 우편번호 형식(5자리 숫자)이 아닙니다.")
				String postalCode,
		@Nullable Boolean isDefault) {}

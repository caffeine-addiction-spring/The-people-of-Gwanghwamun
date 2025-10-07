package com.caffeine.gwanghwamun.domain.user.dto;

import jakarta.validation.constraints.Pattern;

public record UserInfoUpdateResDTO(
		String name,
		@Pattern(regexp = "^010\\d{8}$", message = "유효한 휴대폰 번호 형식(010으로 시작, 11자리 숫자)이 아닙니다.")
				String phone) {}

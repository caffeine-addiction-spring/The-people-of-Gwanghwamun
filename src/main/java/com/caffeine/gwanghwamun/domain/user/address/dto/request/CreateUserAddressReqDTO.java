package com.caffeine.gwanghwamun.domain.user.address.dto.request;

import com.caffeine.gwanghwamun.domain.user.address.entity.UserAddress;
import com.caffeine.gwanghwamun.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateUserAddressReqDTO(
		@NotBlank(message = "주소 별칭은 필수 입력값입니다.") String label,
		@NotBlank(message = "수령인은 필수 입력값입니다.") String recipient,
		@NotBlank(message = "전화번호는 필수 입력값입니다.")
				@Pattern(regexp = "^010\\d{8}$", message = "유효한 휴대폰 번호 형식(010으로 시작, 11자리 숫자)이 아닙니다.")
				String phone,
		@NotBlank(message = "주소는 필수 입력값입니다.") String address,
		@NotBlank(message = "우편번호는 필수 입력값입니다.")
				@Pattern(regexp = "^\\d{5}$", message = "유효한 우편번호 형식(5자리 숫자)이 아닙니다.")
				String postalCode,
		Boolean isDefault) {

	public UserAddress toAddress(User user) {
		return UserAddress.builder()
				.user(user)
				.label(label)
				.recipient(recipient)
				.phone(phone)
				.address(address)
				.postalCode(postalCode)
				.isDefault(isDefault != null && isDefault)
				.build();
	}
}

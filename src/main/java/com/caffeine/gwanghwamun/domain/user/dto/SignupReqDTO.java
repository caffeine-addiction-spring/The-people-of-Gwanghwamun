package com.caffeine.gwanghwamun.domain.user.dto;

import com.caffeine.gwanghwamun.domain.user.entity.User;
import com.caffeine.gwanghwamun.domain.user.entity.UserRoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SignupReqDTO(
		@NotBlank(message = "이메일은 필수 입력 값입니다.") @Email(message = "이메일 형식에 맞지 않습니다.") String email,
		@NotBlank(message = "비밀번호는 필수 입력 값입니다.") String password,
		@NotBlank(message = "이름은 필수 입력 값입니다.") String name,
		@NotBlank(message = "전화번호는 필수 입력 값입니다.")
				@Pattern(regexp = "^010\\d{8}$", message = "유효한 휴대폰 번호 형식(010으로 시작, 11자리 숫자)이 아닙니다.")
				String phone) {

	public User toUser(String encodedPassword) {
		return User.builder()
				.email(email)
				.password(encodedPassword)
				.name(name)
				.phone(phone)
				.role(UserRoleEnum.CUSTOMER)
				.build();
	}
}

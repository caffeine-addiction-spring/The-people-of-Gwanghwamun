package com.caffeine.gwanghwamun.domain.user.dto;

import jakarta.validation.constraints.NotBlank;

public record PasswordChangeReqDTO(
		@NotBlank(message = "현재 비밀번호는 필수 입력 값입니다.") String currentPassword,
		@NotBlank(message = "새 비밀번호는 필수 입력 값입니다.") String newPassword) {}

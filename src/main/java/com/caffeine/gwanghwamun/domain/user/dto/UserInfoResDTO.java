package com.caffeine.gwanghwamun.domain.user.dto;

import com.caffeine.gwanghwamun.domain.user.entity.User;
import com.caffeine.gwanghwamun.domain.user.entity.UserRoleEnum;

public record UserInfoResDTO(
		Long userId, String email, String name, String phone, UserRoleEnum role) {
	public static UserInfoResDTO from(User user) {
		return new UserInfoResDTO(
				user.getUserId(), user.getEmail(), user.getName(), user.getPhone(), user.getRole());
	}
}

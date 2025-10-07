package com.caffeine.gwanghwamun.domain.user.controller;

import com.caffeine.gwanghwamun.common.response.ApiResponse;
import com.caffeine.gwanghwamun.common.response.ResponseUtil;
import com.caffeine.gwanghwamun.common.success.SuccessCode;
import com.caffeine.gwanghwamun.domain.user.dto.UserInfoResDTO;
import com.caffeine.gwanghwamun.domain.user.security.UserDetailsImpl;
import com.caffeine.gwanghwamun.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j(topic = "회원")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController {

	private final UserService userService;

	@GetMapping("/me")
	public ResponseEntity<ApiResponse<UserInfoResDTO>> findUserInfo(
			@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return ResponseUtil.successResponse(
				SuccessCode.USER_READ_SUCCESS, userService.findUserInfo(userDetails.getUser()));
	}
}

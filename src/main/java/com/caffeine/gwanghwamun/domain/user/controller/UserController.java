package com.caffeine.gwanghwamun.domain.user.controller;

import com.caffeine.gwanghwamun.common.response.ApiResponse;
import com.caffeine.gwanghwamun.common.response.ResponseUtil;
import com.caffeine.gwanghwamun.common.success.SuccessCode;
import com.caffeine.gwanghwamun.domain.user.dto.UserInfoResDTO;
import com.caffeine.gwanghwamun.domain.user.dto.UserInfoUpdateResDTO;
import com.caffeine.gwanghwamun.domain.user.security.UserDetailsImpl;
import com.caffeine.gwanghwamun.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j(topic = "회원")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController {

	private final UserService userService;

	@Operation(summary = "회원 정보 조회 API")
	@GetMapping("/me")
	public ResponseEntity<ApiResponse<UserInfoResDTO>> findUserInfo(
			@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return ResponseUtil.successResponse(
				SuccessCode.USER_READ_SUCCESS, userService.findUserInfo(userDetails.getUser()));
	}

	@Operation(summary = "회원 정보 수정 API")
	@PatchMapping("/me")
	public ResponseEntity<ApiResponse<UserInfoResDTO>> updateUser(
			@RequestBody @Valid UserInfoUpdateResDTO requestDto,
			@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return ResponseUtil.successResponse(
				SuccessCode.USER_UPDATE_SUCCESS,
				userService.updateUserInfo(userDetails.getUser(), requestDto));
	}
}

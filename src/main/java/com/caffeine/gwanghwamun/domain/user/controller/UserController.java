package com.caffeine.gwanghwamun.domain.user.controller;

import com.caffeine.gwanghwamun.common.response.ApiResponse;
import com.caffeine.gwanghwamun.common.response.ResponseUtil;
import com.caffeine.gwanghwamun.common.security.model.UserDetailsImpl;
import com.caffeine.gwanghwamun.common.success.SuccessCode;
import com.caffeine.gwanghwamun.domain.user.dto.request.PasswordChangeReqDTO;
import com.caffeine.gwanghwamun.domain.user.dto.request.UserInfoUpdateReqDTO;
import com.caffeine.gwanghwamun.domain.user.dto.response.UserInfoResDTO;
import com.caffeine.gwanghwamun.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j(topic = "회원")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController {

	private final UserService userService;

	@Operation(
			summary = "회원 정보 조회 API",
			description = "로그인한 회원의 상세 정보를 조회한다."
	)
	@GetMapping("/me")
	public ResponseEntity<ApiResponse<UserInfoResDTO>> findUserInfo(
			@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return ResponseUtil.successResponse(
				SuccessCode.USER_READ_SUCCESS, userService.findUserInfo(userDetails.getUser()));
	}

	@Operation(
			summary = "회원 정보 수정 API",
			description = "회원이 자신의 프로필 정보를 수정한다."
	)
	@PatchMapping("/me")
	public ResponseEntity<ApiResponse<UserInfoResDTO>> updateUser(
			@RequestBody @Valid UserInfoUpdateReqDTO requestDto,
			@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return ResponseUtil.successResponse(
				SuccessCode.USER_UPDATE_SUCCESS,
				userService.updateUserInfo(userDetails.getUser(), requestDto));
	}

	@Operation(
			summary = "비밀번호 수정 API",
			description = "회원이 현재 비밀번호를 검증 후 새 비밀번호로 변경한다."
	)
	@PatchMapping("/me/password")
	public ResponseEntity<ApiResponse<Void>> updatePassword(
			@RequestBody @Valid PasswordChangeReqDTO requestDto,
			@AuthenticationPrincipal UserDetailsImpl userDetails) {

		userService.updatePassword(userDetails.getUser(), requestDto);
		return ResponseUtil.successResponse(SuccessCode.PASSWORD_UPDATE_SUCCESS);
	}

	@Operation(
			summary = "회원 삭제 API",
			description = "관리자가 특정 회원 계정을 삭제한다."
	)
	@DeleteMapping("/{userId}")
	@PreAuthorize("hasRole('MASTER')")
	public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long userId) {
		userService.deleteUser(userId);
		return ResponseUtil.successResponse(SuccessCode.USER_DELETE_SUCCESS);
	}
}

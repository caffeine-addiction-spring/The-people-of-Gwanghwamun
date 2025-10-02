package com.caffeine.gwanghwamun.domain.user.controller;

import com.caffeine.gwanghwamun.common.response.ApiResponse;
import com.caffeine.gwanghwamun.common.response.ResponseUtil;
import com.caffeine.gwanghwamun.common.success.SuccessCode;
import com.caffeine.gwanghwamun.domain.user.dto.SignupReqDTO;
import com.caffeine.gwanghwamun.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class UserController {

	private final UserService userService;

	@PostMapping("/auth/signup")
	@Operation(summary = "회원가입 API")
	public ResponseEntity<ApiResponse<Void>> signup(@RequestBody @Valid SignupReqDTO requestDto) {
		userService.signUp(requestDto);
		return ResponseUtil.successResponse(SuccessCode.USER_SAVE_SUCCESS);
	}
}

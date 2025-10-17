package com.caffeine.gwanghwamun.domain.user.controller;

import com.caffeine.gwanghwamun.common.response.ApiResponse;
import com.caffeine.gwanghwamun.common.response.ResponseUtil;
import com.caffeine.gwanghwamun.common.success.SuccessCode;
import com.caffeine.gwanghwamun.domain.user.dto.request.SignupReqDTO;
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

@Slf4j(topic = "인증/인가")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController {

	private final UserService userService;

	@PostMapping("/signup")
	@Operation(summary = "회원가입 API", description = "회원가입을 통해 회원을 생성한다.")
	public ResponseEntity<ApiResponse<Void>> signup(@RequestBody @Valid SignupReqDTO requestDto) {
		userService.signUp(requestDto);
		return ResponseUtil.successResponse(SuccessCode.USER_SAVE_SUCCESS);
	}
}

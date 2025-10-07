package com.caffeine.gwanghwamun.domain.user.controller;

import com.caffeine.gwanghwamun.common.response.ApiResponse;
import com.caffeine.gwanghwamun.common.response.ResponseUtil;
import com.caffeine.gwanghwamun.common.success.SuccessCode;
import com.caffeine.gwanghwamun.domain.user.dto.SignupReqDTO;
import com.caffeine.gwanghwamun.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j(topic = "회원")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class UserController {

	private final UserService userService;

	@PostMapping("/auth/signup")
	@Operation(
			summary = "회원가입 API",
			requestBody =
					@io.swagger.v3.oas.annotations.parameters.RequestBody(
							description = "회원가입 요청 정보",
							required = true,
							content =
									@Content(
											mediaType = "application/json",
											schema = @Schema(implementation = SignupReqDTO.class))))
	@io.swagger.v3.oas.annotations.responses.ApiResponse(
			responseCode = "201",
			description = "회원가입 성공")
	public ResponseEntity<ApiResponse<Void>> signup(@RequestBody @Valid SignupReqDTO requestDto) {
		userService.signUp(requestDto);
		return ResponseUtil.successResponse(SuccessCode.USER_SAVE_SUCCESS);
	}
}

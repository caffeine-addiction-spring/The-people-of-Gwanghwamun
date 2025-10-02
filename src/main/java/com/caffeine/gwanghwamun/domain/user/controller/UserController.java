package com.caffeine.gwanghwamun.domain.user.controller;

import com.caffeine.gwanghwamun.domain.user.dto.SignupReqDTO;
import com.caffeine.gwanghwamun.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
	@Parameters({
		@Parameter(
				name = "email",
				description = "회원 이메일",
				content = @Content(schema = @Schema(implementation = String.class))),
		@Parameter(
				name = "password",
				description = "회원 비밀번호",
				content = @Content(schema = @Schema(implementation = String.class))),
		@Parameter(
				name = "name",
				description = "회원 이름",
				content = @Content(schema = @Schema(implementation = String.class))),
		@Parameter(
				name = "phone",
				description = "회원 전화번호",
				content = @Content(schema = @Schema(implementation = String.class)))
	})
	@ApiResponse(responseCode = "201", description = "회원가입 성공")
	public ResponseEntity<Void> signup(@Valid SignupReqDTO requestDto) {
		userService.signUp(requestDto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}

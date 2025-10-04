package com.caffeine.gwanghwamun.domain.user.controller;

import com.caffeine.gwanghwamun.domain.user.security.UserDetailsImpl;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/test")
public class AuthTestController {

	// 인증 토큰 필요 없는 경우
	@GetMapping("/public")
	public ResponseEntity<Map<String, String>> publicEndpoint() {
		Map<String, String> response = new HashMap<>();
		response.put("message", "This is a public endpoint - No authentication required");
		response.put("timestamp", String.valueOf(System.currentTimeMillis()));
		return ResponseEntity.ok(response);
	}

	// 인증 객체 사용하는 경우
	@GetMapping("/protected")
	public ResponseEntity<Map<String, Object>> protectedEndpoint(
			@AuthenticationPrincipal UserDetailsImpl userDetails) {

		Map<String, Object> response = new HashMap<>();
		response.put("message", "This is a protected endpoint");
		response.put("email", userDetails.getUsername());
		response.put("role", userDetails.getUser().getRole());
		response.put("authorities", userDetails.getAuthorities());

		log.info(
				"Authenticated user: {}, Role: {}",
				userDetails.getUsername(),
				userDetails.getUser().getRole());

		return ResponseEntity.ok(response);
	}

	// ROLE_CUSTOMER
	@GetMapping("/customer")
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<Map<String, String>> customerOnly() {
		Map<String, String> response = new HashMap<>();
		response.put("message", "Customer only endpoint");
		return ResponseEntity.ok(response);
	}

	// ROLE_MANAGER
	@GetMapping("/manager")
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<Map<String, String>> managerOnly() {
		Map<String, String> response = new HashMap<>();
		response.put("message", "Manager only endpoint");
		return ResponseEntity.ok(response);
	}

	// ROLE_OWNER
	@GetMapping("/owner")
	@PreAuthorize("hasRole('OWNER')")
	public ResponseEntity<Map<String, String>> ownerOnly() {
		Map<String, String> response = new HashMap<>();
		response.put("message", "Owner only endpoint");
		return ResponseEntity.ok(response);
	}

	// ROLE_MASTER
	@GetMapping("/master")
	@PreAuthorize("hasRole('MASTER')")
	public ResponseEntity<Map<String, String>> masterOnly() {
		Map<String, String> response = new HashMap<>();
		response.put("message", "Master only endpoint");
		return ResponseEntity.ok(response);
	}
}

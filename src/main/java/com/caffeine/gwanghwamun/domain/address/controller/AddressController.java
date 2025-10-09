package com.caffeine.gwanghwamun.domain.address.controller;

import com.caffeine.gwanghwamun.common.response.ApiResponse;
import com.caffeine.gwanghwamun.common.response.ResponseUtil;
import com.caffeine.gwanghwamun.common.success.SuccessCode;
import com.caffeine.gwanghwamun.domain.address.dto.request.CreateAddressReqDTO;
import com.caffeine.gwanghwamun.domain.address.service.AddressService;
import com.caffeine.gwanghwamun.domain.user.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j(topic = "회원 주소")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users/me/addresses")
public class AddressController {

	private final AddressService addressService;

	@Operation(summary = "주소 생성 API")
	@PostMapping("")
	@PreAuthorize("hasAnyRole('MASTER', 'CUSTOMER')")
	public ResponseEntity<ApiResponse<Void>> createAddress(
			@RequestBody @Valid CreateAddressReqDTO requestDto,
			@AuthenticationPrincipal UserDetailsImpl userDetails) {
		addressService.createAddress(userDetails.getUser(), requestDto);
		return ResponseUtil.successResponse(SuccessCode.ADDRESS_SAVE_SUCCESS);
	}
}

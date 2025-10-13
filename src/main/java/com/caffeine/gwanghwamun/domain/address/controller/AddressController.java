package com.caffeine.gwanghwamun.domain.address.controller;

import com.caffeine.gwanghwamun.common.response.ApiResponse;
import com.caffeine.gwanghwamun.common.response.ResponseUtil;
import com.caffeine.gwanghwamun.common.success.SuccessCode;
import com.caffeine.gwanghwamun.domain.address.dto.request.CreateAddressReqDTO;
import com.caffeine.gwanghwamun.domain.address.dto.request.UpdateAddressReqDTO;
import com.caffeine.gwanghwamun.domain.address.dto.response.DeleteAddressResDTO;
import com.caffeine.gwanghwamun.domain.address.service.AddressService;
import com.caffeine.gwanghwamun.domain.user.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

	@Operation(summary = "주소 수정 API")
	@PutMapping("/{addressId}")
	@PreAuthorize("hasAnyRole('MASTER', 'CUSTOMER')")
	public ResponseEntity<ApiResponse<Void>> updateAddress(
			@PathVariable UUID addressId,
			@RequestBody @Valid UpdateAddressReqDTO requestDto,
			@AuthenticationPrincipal UserDetailsImpl userDetails) {
		addressService.updateAddress(userDetails.getUser(), addressId, requestDto);
		return ResponseUtil.successResponse(SuccessCode.ADDRESS_UPDATE_SUCCESS);
	}

	@Operation(summary = "주소 삭제 API")
	@DeleteMapping("/{addressId}")
	@PreAuthorize("hasAnyRole('MASTER', 'CUSTOMER')")
	public ResponseEntity<ApiResponse<DeleteAddressResDTO>> deleteAddress(
			@PathVariable UUID addressId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		DeleteAddressResDTO response = addressService.deleteAddress(userDetails.getUser(), addressId);
		return ResponseUtil.successResponse(SuccessCode.ADDRESS_DELETE_SUCCESS, response);
	}
}

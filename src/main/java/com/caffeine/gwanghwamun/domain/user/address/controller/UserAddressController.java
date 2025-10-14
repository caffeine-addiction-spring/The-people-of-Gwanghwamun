package com.caffeine.gwanghwamun.domain.user.address.controller;

import com.caffeine.gwanghwamun.common.response.ApiResponse;
import com.caffeine.gwanghwamun.common.response.ResponseUtil;
import com.caffeine.gwanghwamun.common.security.model.UserDetailsImpl;
import com.caffeine.gwanghwamun.common.success.SuccessCode;
import com.caffeine.gwanghwamun.domain.user.address.dto.request.CreateUserAddressReqDTO;
import com.caffeine.gwanghwamun.domain.user.address.dto.request.UpdateUserAddressReqDTO;
import com.caffeine.gwanghwamun.domain.user.address.dto.response.DeleteUserAddressResDTO;
import com.caffeine.gwanghwamun.domain.user.address.dto.response.GetUserAddressListResDTO;
import com.caffeine.gwanghwamun.domain.user.address.service.UserAddressService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
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
public class UserAddressController {

	private final UserAddressService userAddressService;

	@Operation(summary = "주소 생성 API")
	@PostMapping("")
	@PreAuthorize("hasAnyRole('MASTER', 'CUSTOMER')")
	public ResponseEntity<ApiResponse<Void>> createAddress(
			@RequestBody @Valid CreateUserAddressReqDTO requestDto,
			@AuthenticationPrincipal UserDetailsImpl userDetails) {
		userAddressService.createAddress(userDetails.getUser(), requestDto);
		return ResponseUtil.successResponse(SuccessCode.ADDRESS_SAVE_SUCCESS);
	}

	@Operation(summary = "주소 수정 API")
	@PutMapping("/{addressId}")
	@PreAuthorize("hasAnyRole('MASTER', 'CUSTOMER')")
	public ResponseEntity<ApiResponse<Void>> updateAddress(
			@PathVariable UUID addressId,
			@RequestBody @Valid UpdateUserAddressReqDTO requestDto,
			@AuthenticationPrincipal UserDetailsImpl userDetails) {
		userAddressService.updateAddress(userDetails.getUser(), addressId, requestDto);
		return ResponseUtil.successResponse(SuccessCode.ADDRESS_UPDATE_SUCCESS);
	}

	@Operation(summary = "주소 삭제 API")
	@DeleteMapping("/{addressId}")
	@PreAuthorize("hasAnyRole('MASTER', 'CUSTOMER')")
	public ResponseEntity<ApiResponse<DeleteUserAddressResDTO>> deleteAddress(
			@PathVariable UUID addressId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		DeleteUserAddressResDTO response =
				userAddressService.deleteAddress(userDetails.getUser(), addressId);
		return ResponseUtil.successResponse(SuccessCode.ADDRESS_DELETE_SUCCESS, response);
	}

	@Operation(summary = "주소 목록 조회 API")
	@GetMapping("")
	@PreAuthorize("hasAnyRole('MASTER', 'CUSTOMER')")
	public ResponseEntity<ApiResponse<List<GetUserAddressListResDTO>>> getAddressList(
			@AuthenticationPrincipal UserDetailsImpl userDetails) {
		List<GetUserAddressListResDTO> response =
				userAddressService.getUserAddresses(userDetails.getUser());
		return ResponseUtil.successResponse(SuccessCode.ADDRESS_LIST_FETCH_SUCCESS, response);
	}

	@Operation(summary = "주소 상세 조회 API")
	@GetMapping("/{addressId}")
	@PreAuthorize("hasAnyRole('MASTER', 'CUSTOMER')")
	public ResponseEntity<ApiResponse<GetUserAddressListResDTO>> getAddress(
			@PathVariable UUID addressId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		GetUserAddressListResDTO response =
				userAddressService.getUserAddress(userDetails.getUser(), addressId);
		return ResponseUtil.successResponse(SuccessCode.ADDRESS_FETCH_SUCCESS, response);
	}

	@Operation(summary = "기본 배송지 주소 설정 API")
	@PostMapping("/{addressId}")
	@PreAuthorize("hasAnyRole('MASTER', 'CUSTOMER')")
	public ResponseEntity<ApiResponse<Void>> setDefaultAddress(
			@PathVariable UUID addressId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		userAddressService.setDefaultAddress(userDetails.getUser(), addressId);
		return ResponseUtil.successResponse(SuccessCode.DEFAULT_ADDRESS_SAVE_SUCCESS);
	}
}

package com.caffeine.gwanghwamun.domain.store.controller;

import com.caffeine.gwanghwamun.common.response.ApiResponse;
import com.caffeine.gwanghwamun.common.response.ResponseUtil;
import com.caffeine.gwanghwamun.common.success.SuccessCode;
import com.caffeine.gwanghwamun.domain.store.dto.request.StoreCreateReqDTO;
import com.caffeine.gwanghwamun.domain.store.dto.request.StoreUpdateReqDTO;
import com.caffeine.gwanghwamun.domain.store.dto.response.StoreCreateResDTO;
import com.caffeine.gwanghwamun.domain.store.dto.response.StoreDetailResDTO;
import com.caffeine.gwanghwamun.domain.store.dto.response.StoreListResDTO;
import com.caffeine.gwanghwamun.domain.store.dto.response.StoreUpdateResDTO;
import com.caffeine.gwanghwamun.domain.store.service.StoreService;
import com.caffeine.gwanghwamun.domain.user.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/stores")
@RequiredArgsConstructor
public class StoreController {

	private final StoreService storeService;

	@Operation(summary = "가게 등록 API")
	@PreAuthorize("hasAnyRole('OWNER', 'MANAGER', 'MASTER')")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StoreCreateResDTO> createStore(
			@Valid @RequestBody StoreCreateReqDTO request,
			@AuthenticationPrincipal UserDetailsImpl user) {

		StoreCreateResDTO response = storeService.createStore(request, user.getUser());

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@Operation(summary = "가게 목록 조회 API")
	@GetMapping
	public ResponseEntity<Page<StoreListResDTO>> getStoreList(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "createdAt") String sortBy,
			@RequestParam(defaultValue = "desc") String direction) {

		if (size != 10 && size != 30 && size != 50) {
			size = 10;
		}
		Page<StoreListResDTO> stores = storeService.getStoreList(page, size, sortBy, direction);
		return ResponseEntity.ok(stores);
	}

	@Operation(summary = "가게 상세 조회 API")
	@GetMapping("/{storeId}")
	public ResponseEntity<StoreDetailResDTO> getStoreDetail(
			@PathVariable UUID storeId,
			@RequestParam(defaultValue = "name") String sort,
			@RequestParam(defaultValue = "asc") String order) {
		StoreDetailResDTO response = storeService.getStoreDetail(storeId, sort, order);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "가게 수정 API")
	@PreAuthorize("hasAnyRole('OWNER', 'MANAGER', 'MASTER')")
	@PutMapping("/{storeId}")
	public ResponseEntity<StoreUpdateResDTO> updateStore(
			@PathVariable UUID storeId,
			@RequestBody @Valid StoreUpdateReqDTO request,
			@AuthenticationPrincipal UserDetailsImpl user) {
		StoreUpdateResDTO response = storeService.updateStore(storeId, request, user.getUser());
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "가게 삭제 API")
	@PreAuthorize("hasAnyRole('OWNER', 'MANAGER', 'MASTER')")
	@DeleteMapping("/{storeId}")
	public ResponseEntity<ApiResponse<Void>> deleteStore(
			@PathVariable UUID storeId, @AuthenticationPrincipal UserDetailsImpl user) {
		storeService.deleteStore(storeId, user.getUser());
		return ResponseUtil.successResponse(SuccessCode.STORE_DELETE_SUCCESS);
	}
}

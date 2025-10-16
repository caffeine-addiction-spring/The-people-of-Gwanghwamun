package com.caffeine.gwanghwamun.domain.store.controller;

import com.caffeine.gwanghwamun.common.response.ApiResponse;
import com.caffeine.gwanghwamun.common.response.ResponseUtil;
import com.caffeine.gwanghwamun.common.security.model.UserDetailsImpl;
import com.caffeine.gwanghwamun.common.success.SuccessCode;
import com.caffeine.gwanghwamun.domain.store.dto.request.StoreCreateReqDTO;
import com.caffeine.gwanghwamun.domain.store.dto.request.StoreUpdateReqDTO;
import com.caffeine.gwanghwamun.domain.store.dto.response.*;
import com.caffeine.gwanghwamun.domain.store.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/stores")
@RequiredArgsConstructor
public class StoreController {

	private final StoreService storeService;

	@Operation(summary = "가게 등록 API", description = "가게를 등록할 수 있다")
	@PreAuthorize("hasAnyRole('OWNER', 'MANAGER', 'MASTER')")
	@PostMapping
	public ResponseEntity<ApiResponse<StoreCreateResDTO>> createStore(
			@Valid @RequestBody StoreCreateReqDTO request,
			@AuthenticationPrincipal UserDetailsImpl user) {

		StoreCreateResDTO response = storeService.createStore(request, user.getUser());
		return ResponseUtil.successResponse(SuccessCode.STORE_CREATE_SUCCESS, response);
	}

	@Operation(summary = "가게 목록 조회 API", description = "가게 목록을 조회한다")
	@GetMapping
	public ResponseEntity<ApiResponse<Page<StoreListResDTO>>> getStoreList(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "createdAt") String sortBy,
			@RequestParam(defaultValue = "desc") String direction) {

		if (size != 10 && size != 30 && size != 50) {
			size = 10;
		}
		Page<StoreListResDTO> stores = storeService.getStoreList(page, size, sortBy, direction);
		return ResponseUtil.successResponse(SuccessCode.STORE_LIST_SUCCESS, stores);
	}

	@Operation(summary = "가게 상세 조회 API", description = "가게를 상세 조회한다")
	@GetMapping("/{storeId}")
	public ResponseEntity<ApiResponse<StoreDetailResDTO>> getStoreDetail(
			@PathVariable UUID storeId,
			@RequestParam(defaultValue = "name") String sort,
			@RequestParam(defaultValue = "asc") String order) {
		StoreDetailResDTO response = storeService.getStoreDetail(storeId, sort, order);
		return ResponseUtil.successResponse(SuccessCode.STORE_FIND_SUCCESS, response);
	}

	@Operation(summary = "가게 수정 API", description = "가게 정보를 수정할 수 있다")
	@PreAuthorize("hasAnyRole('OWNER', 'MANAGER', 'MASTER')")
	@PutMapping("/{storeId}")
	public ResponseEntity<ApiResponse<StoreUpdateResDTO>> updateStore(
			@PathVariable UUID storeId,
			@RequestBody @Valid StoreUpdateReqDTO request,
			@AuthenticationPrincipal UserDetailsImpl user) {
		StoreUpdateResDTO response = storeService.updateStore(storeId, request, user.getUser());
		return ResponseUtil.successResponse(SuccessCode.STORE_UPDATE_SUCCESS, response);
	}

	@Operation(summary = "가게 삭제 API", description = "가게를 삭제할 수 있다")
	@PreAuthorize("hasAnyRole('OWNER', 'MANAGER', 'MASTER')")
	@DeleteMapping("/{storeId}")
	public ResponseEntity<ApiResponse<Void>> deleteStore(
			@PathVariable UUID storeId, @AuthenticationPrincipal UserDetailsImpl user) {
		storeService.deleteStore(storeId, user.getUser());
		return ResponseUtil.successResponse(SuccessCode.STORE_DELETE_SUCCESS);
	}

	@Operation(summary = "가게 검색 API", description = "키워드로 가게를 검색할 수 있다")
	@GetMapping("/search")
	public ResponseEntity<ApiResponse<Page<StoreListResDTO>>> searchStores(
			@RequestParam String keyword,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "name") String sortBy,
			@RequestParam(defaultValue = "asc") String direction) {

		Page<StoreListResDTO> result =
				storeService.searchStores(keyword, page, size, sortBy, direction);
		return ResponseUtil.successResponse(SuccessCode.STORE_SEARCH_SUCCESS, result);
	}
}

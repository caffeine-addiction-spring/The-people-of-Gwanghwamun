package com.caffeine.gwanghwamun.domain.region.controller;

import com.caffeine.gwanghwamun.common.response.ApiResponse;
import com.caffeine.gwanghwamun.common.response.ResponseUtil;
import com.caffeine.gwanghwamun.common.success.SuccessCode;
import com.caffeine.gwanghwamun.domain.region.dto.request.RegionReqDTO;
import com.caffeine.gwanghwamun.domain.region.dto.response.RegionResDTO;
import com.caffeine.gwanghwamun.domain.region.service.RegionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/regions")
@RequiredArgsConstructor
public class RegionController {

	private final RegionService regionService;

	@Operation(summary = "지역 목록 조회 API")
	@GetMapping
	public ResponseEntity<ApiResponse<List<RegionResDTO>>> getRegion() {
		List<RegionResDTO> response = regionService.getAllRegion();
		return ResponseUtil.successResponse(SuccessCode.REGION_LIST_SUCCESS, response);
	}

	@Operation(summary = "지역 생성 API")
	@PreAuthorize("hasAnyRole('MANAGER', 'MASTER')")
	@PostMapping
	public ResponseEntity<ApiResponse<RegionResDTO>> createRegion(
			@Valid @RequestBody RegionReqDTO regionReqDTO) {
		RegionResDTO response = regionService.createRegion(regionReqDTO);
		return ResponseUtil.successResponse(SuccessCode.REGION_CREATE_SUCCESS, response);
	}

	@Operation(summary = "지역 수정 API")
	@PreAuthorize("hasAnyRole('MANAGER', 'MASTER')")
	@PutMapping("/{addressId}")
	public ResponseEntity<ApiResponse<RegionResDTO>> updateRegion(
			@PathVariable UUID addressId, @Valid @RequestBody RegionReqDTO request) {
		RegionResDTO response = regionService.updateRegion(addressId, request);
		return ResponseUtil.successResponse(SuccessCode.REGION_UPDATE_SUCCESS, response);
	}
}

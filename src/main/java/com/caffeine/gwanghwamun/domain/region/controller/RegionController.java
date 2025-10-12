package com.caffeine.gwanghwamun.domain.region.controller;

import com.caffeine.gwanghwamun.domain.region.dto.request.RegionCreateReqDTO;
import com.caffeine.gwanghwamun.domain.region.dto.response.RegionResDTO;
import com.caffeine.gwanghwamun.domain.region.service.RegionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
	public ResponseEntity<List<RegionResDTO>> getRegion() {
		List<RegionResDTO> response = regionService.getAllRegion();
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "지역 생성 API")
	@PreAuthorize("hasAnyRole('MANAGER', 'MASTER')")
	@PostMapping
	public ResponseEntity<RegionResDTO> createRegion(
			@Valid @RequestBody RegionCreateReqDTO regionReqDTO) {
		RegionResDTO response = regionService.createRegion(regionReqDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}

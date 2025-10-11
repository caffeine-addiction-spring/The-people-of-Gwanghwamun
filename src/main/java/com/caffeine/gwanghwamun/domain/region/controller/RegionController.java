package com.caffeine.gwanghwamun.domain.region.controller;

import com.caffeine.gwanghwamun.domain.region.dto.RegionListResDTO;
import com.caffeine.gwanghwamun.domain.region.service.RegionService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/regions")
@RequiredArgsConstructor
public class RegionController {

	private final RegionService regionService;

	@Operation(summary = "지역 목록 조회 API")
	@GetMapping
	public ResponseEntity<List<RegionListResDTO>> getRegion() {
		List<RegionListResDTO> response = regionService.getAllRegion();
		return ResponseEntity.ok(response);
	}
}

package com.caffeine.gwanghwamun.domain.ai.controller;

import com.caffeine.gwanghwamun.common.response.ApiResponse;
import com.caffeine.gwanghwamun.common.response.ResponseUtil;
import com.caffeine.gwanghwamun.common.success.SuccessCode;
import com.caffeine.gwanghwamun.domain.ai.dto.request.AiPromptReqDTO;
import com.caffeine.gwanghwamun.domain.ai.dto.request.AiUpdateReqDTO;
import com.caffeine.gwanghwamun.domain.ai.dto.response.AiResDTO;
import com.caffeine.gwanghwamun.domain.ai.service.AiService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/ai")
public class AiController {

	private final AiService aiService;

	@Operation(summary = "AI를 통한 상품 설명 생성 API", description = "AI를 통해 상품 설명을 생성한다.")
	@PostMapping("/description")
	public ResponseEntity<ApiResponse<String>> generateDescription(
			@RequestBody @Valid AiPromptReqDTO request) {
		String result = aiService.ask(request.getPrompt());
		return ResponseUtil.successResponse(SuccessCode.AI_SAVE_SUCCESS, result);
	}

	@Operation(summary = "AI 결과 단일 조회 API", description = "AI 결과를 조회한다.")
	@GetMapping("/{uuid}")
	public ResponseEntity<ApiResponse<AiResDTO>> getAiResult(@PathVariable UUID uuid) {
		AiResDTO result = aiService.getAiResult(uuid);
		return ResponseUtil.successResponse(SuccessCode.AI_READ_SUCCESS, result);
	}

	@Operation(summary = "AI 결과 수정 API", description = "AI 결과를 수정한다.")
	@PutMapping("/{uuid}")
	public ResponseEntity<ApiResponse<Void>> updateAiResult(
			@PathVariable UUID uuid, @RequestBody AiUpdateReqDTO updateReqDTO) {
		aiService.updateAiResult(uuid, updateReqDTO);
		return ResponseUtil.successResponse(SuccessCode.AI_UPDATE_SUCCESS);
	}

	@Operation(summary = "AI 결과 삭제 API", description = "AI 결과를 삭제한다.")
	@DeleteMapping("/{uuid}")
	public ResponseEntity<ApiResponse<Void>> deleteAiResult(@PathVariable UUID uuid) {
		aiService.deleteAiResult(uuid);
		return ResponseUtil.successResponse(SuccessCode.AI_DELETE_SUCCESS);
	}

	@Operation(summary = "AI 결과 검색 API", description = "AI 결과를 검색한다.")
	@GetMapping("/search")
	public ResponseEntity<ApiResponse<Page<AiResDTO>>> searchAiResults(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "createAt") String sortBy,
			@RequestParam(defaultValue = "desc") String direction) {

		Page<AiResDTO> results = aiService.searchAiResults(page, size, sortBy, direction);
		return ResponseUtil.successResponse(SuccessCode.AI_READ_SUCCESS, results);
	}
}

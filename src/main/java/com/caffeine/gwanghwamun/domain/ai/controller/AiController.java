package com.caffeine.gwanghwamun.domain.ai.controller;

import com.caffeine.gwanghwamun.common.response.ApiResponse;
import com.caffeine.gwanghwamun.common.response.ResponseUtil;
import com.caffeine.gwanghwamun.common.success.SuccessCode;
import com.caffeine.gwanghwamun.domain.ai.dto.AiResDTO;
import com.caffeine.gwanghwamun.domain.ai.dto.request.AiPromptReqDTO;
import com.caffeine.gwanghwamun.domain.ai.service.AiService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @Operation(summary = "AI 응답 단일 조회 API", description = "AI 응답을 조회한다.")
    @GetMapping("/{uuid}")
    public ResponseEntity<ApiResponse<AiResDTO>> getAiResult(@PathVariable UUID aiResultId) {
        AiResDTO result = aiService.getAiResult(aiResultId);
        return ResponseUtil.successResponse(SuccessCode.AI_READ_SUCCESS, result);
    }

}

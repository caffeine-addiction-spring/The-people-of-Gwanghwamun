package com.caffeine.gwanghwamun.domain.ai.controller;

import com.caffeine.gwanghwamun.common.response.ApiResponse;
import com.caffeine.gwanghwamun.common.response.ResponseUtil;
import com.caffeine.gwanghwamun.common.success.SuccessCode;
import com.caffeine.gwanghwamun.domain.ai.dto.request.AiPromptReqDTO;
import com.caffeine.gwanghwamun.domain.ai.service.AiService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/ai")
public class AiController {

	private final AiService aiService;

	@Operation(summary = "AI를 통한 상품 설명 생성 API")
	@PostMapping("/description")
	public ResponseEntity<ApiResponse<String>> generateDescription(
			@RequestBody @Valid AiPromptReqDTO request) {
		String result = aiService.ask(request.getPrompt());
		return ResponseUtil.successResponse(SuccessCode.AI_SAVE_SUCCESS, result);
	}
}

package com.caffeine.gwanghwamun.domain.ai.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AiPromptReqDTO {
	@NotBlank private String prompt;
}

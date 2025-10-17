package com.caffeine.gwanghwamun.domain.ai.dto.response;

import com.caffeine.gwanghwamun.domain.ai.entity.Ai;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AiResDTO {

	private UUID aiResultId;
	private String question;
	private String answer;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public static AiResDTO fromEntity(Ai ai) {
		AiResDTO aiResDTO = new AiResDTO();
		aiResDTO.setAiResultId(ai.getAiResultId());
		aiResDTO.setQuestion(ai.getQuestion());
		aiResDTO.setAnswer(ai.getAnswer());
		aiResDTO.setCreatedAt(ai.getCreateAt());
		aiResDTO.setUpdatedAt(ai.getLastUpdatedAt());
		return aiResDTO;
	}
}

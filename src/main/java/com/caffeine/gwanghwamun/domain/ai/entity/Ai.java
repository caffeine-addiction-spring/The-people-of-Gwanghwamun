package com.caffeine.gwanghwamun.domain.ai.entity;

import com.caffeine.gwanghwamun.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "p_ai_results")
public class Ai extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "ai_result_id")
	private UUID aiResultId;

	@Column(name = "question", nullable = false)
	private String question;

	@Column(name = "answer", nullable = false)
	private String answer;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	public static Ai create(String question, String answer) {
		Ai ai = new Ai();
		ai.question = question;
		ai.answer = answer;
		return ai;
	}
}

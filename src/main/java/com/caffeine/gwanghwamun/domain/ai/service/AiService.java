package com.caffeine.gwanghwamun.domain.ai.service;

import com.caffeine.gwanghwamun.domain.ai.entity.Ai;
import com.caffeine.gwanghwamun.domain.ai.repository.AiRepository;
import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiService {

	private final AiRepository aiRepository;

	@Value("${AI_API_KEY}")
	private String apiKey;

	private Client client;

	@PostConstruct
	public void init() {
		this.client = Client.builder().apiKey(apiKey).build();
	}

	public String ask(String prompt) {
		Content content =
				Content.builder()
						.parts(
								Part.fromText(
										"""
												오직 상품 홍보 문구만 생성해줘.
												아래 조건을 반드시 지켜야 해:
												- 인사말, 설명, 부연 문장, 번호, 구분선 금지
												- 문구만 한 줄로 출력
												- 50자 이하로 간결하게 작성
												- 소비자의 구매 욕구를 자극하는 문체로

												상품 설명 키워드: %s
												"""
												.formatted(prompt)))
						.build();

		GenerateContentResponse response =
				client.models.generateContent("models/gemini-2.5-flash", content, null);

		String answer = response.text();

		Ai ai = Ai.create(prompt, answer);
		aiRepository.save(ai);

		return answer;
	}
}

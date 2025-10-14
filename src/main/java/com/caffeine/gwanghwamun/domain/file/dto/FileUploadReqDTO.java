package com.caffeine.gwanghwamun.domain.file.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FileUploadReqDTO {
	@NotBlank private String gid;
	private String location;
	private boolean imageOnly; // 이미지 형식만 가능하게 통제
	private boolean single; // 단일파일 업로드
}

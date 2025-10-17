package com.caffeine.gwanghwamun.domain.file.dto;

import lombok.Data;

@Data
public class FileUploadReqDTO {
	private String gid;
	private String location;
	private boolean imageOnly; // 이미지 형식만 가능하게 통제
	private boolean single; // 단일파일 업로드
}

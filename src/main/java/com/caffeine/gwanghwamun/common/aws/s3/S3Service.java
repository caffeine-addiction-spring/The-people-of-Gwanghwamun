package com.caffeine.gwanghwamun.common.aws.s3;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class S3Service {

	private final S3Util s3Util;

	@Value("${aws.s3.bucket}")
	private String bucketName;

	public S3Service(S3Util s3Util) {
		this.s3Util = s3Util;
	}

	public String uploadImage(MultipartFile file, String folder) throws IOException {
		if (!isValidImageFile(file)) {
			throw new IllegalArgumentException("지원하지 않는 이미지 형식입니다.");
		}
		if (file.getSize() > 10 * 1024 * 1024) {
			throw new IllegalArgumentException("파일 크기가 너무 큽니다. (최대 10MB)");
		}
		String contentType = file.getContentType() != null ? file.getContentType() : "image/*";
		return s3Util.uploadFile(
				file.getBytes(), safeName(file.getOriginalFilename()), folder, contentType);
	}

	public String uploadFile(MultipartFile file, String folder) throws IOException {
		if (file.getSize() > 50 * 1024 * 1024) {
			throw new IllegalArgumentException("파일 크기가 너무 큽니다. (최대 50MB)");
		}
		String contentType =
				file.getContentType() != null ? file.getContentType() : "application/octet-stream";
		return s3Util.uploadFile(
				file.getBytes(), safeName(file.getOriginalFilename()), folder, contentType);
	}

	public String buildPublicUrl(String key) {
		return "https://" + bucketName + ".s3.ap-northeast-2.amazonaws.com/" + key;
	}

	private boolean isValidImageFile(MultipartFile file) {
		String contentType = file.getContentType();
		return contentType != null && contentType.startsWith("image/");
	}

	private String safeName(String originalFileName) {
		return originalFileName == null ? "unknown" : originalFileName;
	}
}

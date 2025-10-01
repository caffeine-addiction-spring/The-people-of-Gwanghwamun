package com.caffeine.gwanghwamun.common.aws.s3;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
@ConditionalOnBean(S3Client.class)
public class S3Util {

	private final S3Client s3Client;

	@Value("${aws.s3.bucket}")
	private String bucketName;

	public S3Util(S3Client s3Client) {
		this.s3Client = s3Client;
	}

	public String uploadFile(
			byte[] fileBytes, String originalFileName, String folder, String contentType) {
		String fileName = generateFileName(originalFileName);
		String key = folder + "/" + fileName;

		PutObjectRequest putObjectRequest =
				PutObjectRequest.builder()
						.bucket(bucketName)
						.key(key)
						.contentType(contentType)
						.acl(ObjectCannedACL.PUBLIC_READ)
						.build();

		s3Client.putObject(putObjectRequest, RequestBody.fromBytes(fileBytes));

		return buildPublicUrl(key);
	}

	public String buildPublicUrl(String key) {
		return "https://" + bucketName + ".s3.ap-northeast-2.amazonaws.com/" + key;
	}

	public String extractS3KeyFromUrl(String url) {
		int domainEndIdx = url.indexOf(".com/");
		return domainEndIdx != -1 ? url.substring(domainEndIdx + 5) : null;
	}

	private String buildKey(String folder, String fileName) {
		String safeFolder = sanitizeFolder(folder);
		return safeFolder.isEmpty() ? fileName : safeFolder + "/" + fileName;
	}

	private String sanitizeFolder(String folder) {
		String s = folder == null ? "" : folder.trim().toLowerCase();
		s = s.replaceAll("\\s+", "-").replaceAll("[^a-z0-9\\-/]", "").replaceAll("-{2,}", "-");
		return s.replaceAll("^[-/]+|[-/]+$", "");
	}

	private String generateFileName(String originalFileName) {
		int dot = originalFileName == null ? -1 : originalFileName.lastIndexOf(".");
		String extension = dot >= 0 ? originalFileName.substring(dot).toLowerCase() : "";
		return UUID.randomUUID() + extension;
	}
}

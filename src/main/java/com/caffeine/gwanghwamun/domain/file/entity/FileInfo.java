package com.caffeine.gwanghwamun.domain.file.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(
		name = "p_file_info",
		indexes = {
			@Index(name = "idx_file_info_1", columnList = "gid,createdAt"),
			@Index(name = "idx_file_info_2", columnList = "gid,location,createdAt")
		})
public class FileInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "file_id")
	private UUID fileId;

	@Column(length = 65, nullable = false)
	private String gid;

	@Column(length = 45)
	private String location;

	@Column(length = 150, nullable = false)
	private String fileName; // 업로드한 원본 파일명

	@Column(length = 65)
	private String contentType; // 파일 형식 image/png ,,

	@Column(length = 45)
	private String extension; // 확장자

	@Column(length = 255)
	private String fileUrl;

	private boolean done; // 그룹작업 완료 여부, true : 유지시킬 필요가 있는 파일

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@CreatedBy
	@Column(updatable = false)
	private String createdBy;

	public void update(
			String gid,
			String location,
			String fileName,
			String contentType,
			String extension,
			String fileUrl) {
		if (gid != null) this.gid = gid;
		if (location != null) this.location = location;
		if (fileName != null) this.fileName = fileName;
		if (contentType != null) this.contentType = contentType;
		if (extension != null) this.extension = extension;
		if (fileUrl != null) this.fileUrl = fileUrl;
		this.done = true;
	}
}

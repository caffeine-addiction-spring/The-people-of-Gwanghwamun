package com.caffeine.gwanghwamun.domain.file.dto.response;

import com.caffeine.gwanghwamun.domain.file.entity.FileInfo;
import java.util.UUID;
import lombok.Data;

@Data
public class FileInfoResDTO {
	private UUID fileId;
	private String gid;
	private String fileName;
	private String fileUrl;
	private boolean done;

	public static FileInfoResDTO fromItem(FileInfo item) {
		FileInfoResDTO resDTO = new FileInfoResDTO();
		resDTO.setFileId(item.getFileId());
		resDTO.setGid(item.getGid());
		resDTO.setFileName(item.getFileName());
		resDTO.setFileUrl(item.getFileUrl());
		resDTO.setDone(item.isDone());
		return resDTO;
	}
}

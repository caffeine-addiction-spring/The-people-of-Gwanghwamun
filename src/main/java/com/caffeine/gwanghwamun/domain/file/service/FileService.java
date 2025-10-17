package com.caffeine.gwanghwamun.domain.file.service;

import com.caffeine.gwanghwamun.common.aws.s3.S3Service;
import com.caffeine.gwanghwamun.common.exception.CustomException;
import com.caffeine.gwanghwamun.common.exception.ErrorCode;
import com.caffeine.gwanghwamun.domain.file.dto.request.FileUpdateReqDTO;
import com.caffeine.gwanghwamun.domain.file.dto.request.FileUploadReqDTO;
import com.caffeine.gwanghwamun.domain.file.dto.response.FileInfoResDTO;
import com.caffeine.gwanghwamun.domain.file.entity.FileInfo;
import com.caffeine.gwanghwamun.domain.file.entity.FileStatus;
import com.caffeine.gwanghwamun.domain.file.repository.FileInfoRepository;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileService {

	private final FileInfoRepository fileInfoRepository;
	private final S3Service s3Service;

	// 파일 업로드 처리
	@Transactional
	public List<FileInfoResDTO> upload(MultipartFile[] files, @Valid FileUploadReqDTO uploadReqDTO) {
		String gid = uploadReqDTO.getGid();
		gid = StringUtils.hasText(gid) ? gid : UUID.randomUUID().toString();
		String location = uploadReqDTO.getLocation();
		boolean imageOnly = uploadReqDTO.isImageOnly();
		boolean single = uploadReqDTO.isSingle();

		if (files == null || files.length == 0) {
			throw new CustomException(ErrorCode.FILE_NOT_UPLOAD);
		}

		if (single) {
			deleteFiles(gid, location);
			files = new MultipartFile[] {files[0]};
		}

		if (imageOnly) {
			files =
					Arrays.stream(files)
							.filter(
									file ->
											file.getContentType() != null && file.getContentType().startsWith("image/"))
							.toArray(MultipartFile[]::new);
		}

		List<FileInfoResDTO> uploadedFiles = new ArrayList<>();
		for (MultipartFile file : files) {
			try {
				String fileName = file.getOriginalFilename();
				String extension = fileName.substring(fileName.lastIndexOf("."));
				String contentType = file.getContentType();

				FileInfo item = new FileInfo();
				item.setGid(gid);
				if (StringUtils.hasText(location)) {
					item.setLocation(location);
				}

				item.setFileName(fileName);
				item.setExtension(extension);
				item.setContentType(contentType);
				item.setDone(false);

				fileInfoRepository.save(item);

				// 폴더 이름 = 도메인별 구분
				String folder = StringUtils.hasText(location) ? location : "default";
				String s3Url = s3Service.uploadFile(file, folder);
				item.setFileUrl(s3Url);

				item.setDone(true);
				fileInfoRepository.save(item);

				uploadedFiles.add(FileInfoResDTO.fromItem(item));

			} catch (IOException e) {
				throw new CustomException(ErrorCode.FILE_NOT_UPLOAD);
			}
		}

		return uploadedFiles;
	}

	// 단일 파일 조회
	public FileInfoResDTO get(UUID fileuuid) {
		FileInfo item =
				fileInfoRepository
						.findById(fileuuid)
						.orElseThrow(() -> new CustomException(ErrorCode.FILE_NOT_FOUND));
		return FileInfoResDTO.fromItem(item);
	}

	/*
	 * 파일 목록 조회
	 * @param gid : 그룹 ID
	 * @param location : 그룹 내에서 위치
	 * @param status : ALL, UNDONE, DONE
	 * @return 업로드를 성공한 파일 목록 정보
	 */
	public List<FileInfoResDTO> getList(String gid, String location, FileStatus status) {
		status = Objects.requireNonNullElse(status, FileStatus.ALL);

		List<FileInfo> files;

		if (status == FileStatus.ALL) {
			if (StringUtils.hasText(location)) {
				files = fileInfoRepository.findByGidAndLocation(gid, location);
			} else {
				files = fileInfoRepository.findByGid(gid);
			}
		} else {
			boolean done = (status == FileStatus.DONE);
			files = fileInfoRepository.findByGidAndLocationAndDone(gid, location, done);
		}

		return files.stream().map(FileInfoResDTO::fromItem).toList();
	}

	public List<FileInfoResDTO> getList(String gid, String location) {
		return getList(gid, location, FileStatus.DONE);
	}

	@Transactional(readOnly = true)
	public Page<FileInfoResDTO> searchFiles(
			String gid, String location, int page, int size, String sortBy, String direction) {

		if (size != 10 && size != 30 && size != 50) {
			size = 10;
		}

		Sort.Direction sortDirection =
				direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
		Sort sort = Sort.by(sortDirection, sortBy);
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<FileInfo> files;

		if (StringUtils.hasText(location)) {
			files = fileInfoRepository.findByGidAndLocation(gid, location, pageable);
		} else {
			files = fileInfoRepository.findByGid(gid, pageable);
		}

		return files.map(FileInfoResDTO::fromItem);
	}

	// 파일 등록번호로 삭제
	public FileInfoResDTO deleteFile(UUID fileuuid) {
		FileInfoResDTO item = get(fileuuid);
		fileInfoRepository.deleteById(fileuuid);
		return item;
	}

	// 파일 목록 삭제, gid, location
	@Transactional
	public List<FileInfoResDTO> deleteFiles(String gid, String location) {
		List<FileInfoResDTO> files = getList(gid, location, FileStatus.ALL);
		List<FileInfoResDTO> deletedItems = new ArrayList<>();
		for (FileInfoResDTO file : files) {
			deleteFile(file.getFileId());
			deletedItems.add(file);
		}

		return deletedItems;
	}

	private FileInfo getEntity(UUID fileuuid) {
		return fileInfoRepository
				.findById(fileuuid)
				.orElseThrow(() -> new CustomException(ErrorCode.FILE_NOT_FOUND));
	}

	@Transactional
	public void updateFile(UUID fileuuid, FileUpdateReqDTO updateReqDTO) {
		FileInfo fileInfo = getEntity(fileuuid);
		fileInfo.update(
				updateReqDTO.getGid(),
				updateReqDTO.getLocation(),
				updateReqDTO.getFileName(),
				updateReqDTO.getContentType(),
				updateReqDTO.getExtension(),
				updateReqDTO.getFileUrl());
	}
}

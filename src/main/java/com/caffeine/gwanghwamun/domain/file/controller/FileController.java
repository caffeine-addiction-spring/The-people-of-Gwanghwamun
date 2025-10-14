package com.caffeine.gwanghwamun.domain.file.controller;

import com.caffeine.gwanghwamun.common.exception.ErrorCode;
import com.caffeine.gwanghwamun.common.response.ApiResponse;
import com.caffeine.gwanghwamun.common.response.ResponseUtil;
import com.caffeine.gwanghwamun.common.success.SuccessCode;
import com.caffeine.gwanghwamun.domain.file.dto.FileInfoResDTO;
import com.caffeine.gwanghwamun.domain.file.dto.FileUploadReqDTO;
import com.caffeine.gwanghwamun.domain.file.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j(topic = "파일")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/file")
public class FileController {

	private final FileService fileService;

	@Operation(summary = "파일 업로드")
	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ApiResponse<List<FileInfoResDTO>>> upload(
			@RequestPart(name = "file", required = false) MultipartFile[] files,
			@Valid @RequestPart(name = "requestDTO") FileUploadReqDTO requestDTO,
			Errors errors) {
		if (errors.hasErrors()) {
			return ResponseUtil.failureResponse(ErrorCode.VALIDATION_ERROR);
		}

		List<FileInfoResDTO> items = fileService.upload(files, requestDTO);
		return ResponseUtil.successResponse(SuccessCode.FILE_UPLOAD_SUCCESS, items);
	}

	@Operation(summary = "파일 정보 조회")
	@GetMapping("/{uuid}")
	public ResponseEntity<ApiResponse<FileInfoResDTO>> getFile(@PathVariable String uuid) {
		FileInfoResDTO item = fileService.get(UUID.fromString(uuid));
		return ResponseUtil.successResponse(SuccessCode.FILE_READ_SUCCESS, item);
	}

	@Operation(summary = "파일 정보 그룹 조회")
	@GetMapping({"/list/{gid}", "/list/{gid}/{location}"})
	public ResponseEntity<ApiResponse<List<FileInfoResDTO>>> getFileList(
			@PathVariable("gid") String gid,
			@PathVariable(name = "location", required = false) String location) {

		List<FileInfoResDTO> items = fileService.getList(gid, location);

		return ResponseUtil.successResponse(SuccessCode.FILE_READ_SUCCESS, items);
	}

	@Operation(summary = "파일 정보 삭제")
	@DeleteMapping("/{uuid}")
	public ResponseEntity<ApiResponse<Void>> deleteFile(@PathVariable String uuid) {
		fileService.deleteFile(UUID.fromString(uuid));

		return ResponseUtil.successResponse(SuccessCode.FILE_DELETE_SUCCESS);
	}

	@Operation(summary = "파일 정보 그룹 삭제")
	@DeleteMapping({"/deletes/{gid}", "/deletes/{gid}/{location}"})
	public ResponseEntity<ApiResponse<Void>> deleteFileList(
			@PathVariable("gid") String gid,
			@PathVariable(name = "location", required = false) String location) {
		fileService.deleteFiles(gid, location);

		return ResponseUtil.successResponse(SuccessCode.FILE_DELETE_SUCCESS);
	}
}

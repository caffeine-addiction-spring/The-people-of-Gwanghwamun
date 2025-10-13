package com.caffeine.gwanghwamun.domain.file.controller;

import com.caffeine.gwanghwamun.domain.file.dto.FileInfoResDTO;
import com.caffeine.gwanghwamun.domain.file.dto.FileUploadReqDTO;
import com.caffeine.gwanghwamun.domain.file.service.FileService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j(topic = "파일")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/file")
public class FileController {

	private final FileService fileService;

	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public List<FileInfoResDTO> upload(
			@RequestPart(name = "file", required = false) MultipartFile[] files,
			@Valid @RequestPart(name = "requestDTO") FileUploadReqDTO requestDTO,
			Errors errors) {
		if (errors.hasErrors()) {

			List<FileInfoResDTO> items = fileService.upload(files, requestDTO);
			return items;
		}

		return null;
	}
}

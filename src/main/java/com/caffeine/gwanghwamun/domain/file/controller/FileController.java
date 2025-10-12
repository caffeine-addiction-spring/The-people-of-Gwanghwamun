package com.caffeine.gwanghwamun.domain.file.controller;

import com.caffeine.gwanghwamun.domain.file.dto.FileInfoResDTO;
import com.caffeine.gwanghwamun.domain.file.dto.FileUploadReqDTO;
import com.caffeine.gwanghwamun.domain.file.service.FileService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j(topic = "파일")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/file")
public class FileController {

	private final FileService fileService;

	@PostMapping("/upload")
	public List<FileInfoResDTO> upload(
			@RequestPart(name = "file", required = false) MultipartFile[] files,
			@Valid FileUploadReqDTO requestDTO,
			Errors errors) {
		if (errors.hasErrors()) {

			List<FileInfoResDTO> items = fileService.upload(files, requestDTO);
			return items;
		}

		return null;
	}
}

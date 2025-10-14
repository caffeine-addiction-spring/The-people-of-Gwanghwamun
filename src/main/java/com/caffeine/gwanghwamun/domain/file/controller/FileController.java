package com.caffeine.gwanghwamun.domain.file.controller;

import com.caffeine.gwanghwamun.domain.file.dto.FileInfoResDTO;
import com.caffeine.gwanghwamun.domain.file.dto.FileUploadReqDTO;
import com.caffeine.gwanghwamun.domain.file.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

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

    @Operation(summary = "파일 업로드")
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

    @Operation(summary = "파일 정보 조회")
    @GetMapping("/{uuid}")
    public FileInfoResDTO getFile(@PathVariable String uuid) {
        FileInfoResDTO item = fileService.get(UUID.fromString(uuid));
        return item;
    }

    @Operation(summary = "파일 정보 그룹 조회")
    @GetMapping({"/list/{gid}", "/list/{gid}/{location}"})
    public List<FileInfoResDTO> getFileList(
            @PathVariable("gid") String gid,
            @PathVariable(name="location", required = false)
            String location) {

        List<FileInfoResDTO> items = fileService.getList(gid, location);

        return items;
    }

    @Operation(summary = "파일 정보 삭제")
    @DeleteMapping("/{uuid}")
    public FileInfoResDTO deleteFile(@PathVariable String uuid) {
        FileInfoResDTO item = fileService.deleteFile(UUID.fromString(uuid));

        return item;
    }
}

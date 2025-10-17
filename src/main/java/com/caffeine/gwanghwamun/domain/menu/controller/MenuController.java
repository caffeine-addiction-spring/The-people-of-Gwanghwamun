package com.caffeine.gwanghwamun.domain.menu.controller;

import com.caffeine.gwanghwamun.common.response.ApiResponse;
import com.caffeine.gwanghwamun.common.response.ResponseUtil;
import com.caffeine.gwanghwamun.common.security.model.UserDetailsImpl;
import com.caffeine.gwanghwamun.common.success.SuccessCode;
import com.caffeine.gwanghwamun.domain.file.dto.response.FileInfoResDTO;
import com.caffeine.gwanghwamun.domain.file.service.FileService;
import com.caffeine.gwanghwamun.domain.menu.dto.request.MenuCreateReqDTO;
import com.caffeine.gwanghwamun.domain.menu.dto.request.MenuSoldOutReqDTO;
import com.caffeine.gwanghwamun.domain.menu.dto.request.MenuUpdateReqDTO;
import com.caffeine.gwanghwamun.domain.menu.dto.request.MenuVisibilityReqDTO;
import com.caffeine.gwanghwamun.domain.menu.dto.response.MenuResDTO;
import com.caffeine.gwanghwamun.domain.menu.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/stores/{storeId}/menus")
@RequiredArgsConstructor
public class MenuController {

	private final MenuService menuService;
	private final FileService fileService;

	@PreAuthorize("hasAnyRole('OWNER','MANAGER','MASTER')")
	@Operation(summary = "메뉴 생성 API", description = "메뉴를 생성한다.")
	@PostMapping
	public ResponseEntity<ApiResponse<MenuResDTO>> createMenu(
			@PathVariable("storeId") UUID storeId,
			@RequestBody MenuCreateReqDTO menuCreateReqDTO,
			@AuthenticationPrincipal UserDetailsImpl user) {
		MenuResDTO menuResDTO = menuService.saveMenu(storeId, menuCreateReqDTO, user);
		return ResponseUtil.successResponse(SuccessCode.MENU_SAVE_SUCCESS, menuResDTO);
	}

	@Operation(summary = "메뉴 상세 조회 API", description = "메뉴를 상세 조회한다.")
	@GetMapping("/{menuId}")
	public ResponseEntity<ApiResponse<MenuResDTO>> getMenu(
			@PathVariable("storeId") UUID storeId,
			@PathVariable("menuId") UUID menuId,
			@AuthenticationPrincipal UserDetailsImpl user) {
		MenuResDTO menuResDTO = menuService.findMenuById(storeId, menuId, user);
		return ResponseUtil.successResponse(SuccessCode.MENU_FIND_SUCCESS, menuResDTO);
	}

	@Operation(summary = "메뉴 목록 조회 API", description = "메뉴 목록을 조회한다.")
	@GetMapping
	public ResponseEntity<ApiResponse<Page<MenuResDTO>>> getMenuList(
			@PathVariable("storeId") UUID storeId,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size,
			@RequestParam(name = "sortBy", defaultValue = "createAt") String sortBy,
			@RequestParam(name = "direction", defaultValue = "desc") String direction,
			@AuthenticationPrincipal UserDetailsImpl principal) {

		if (size != 10 && size != 30 && size != 50) size = 10;
		Sort.Direction sortDirection =
				direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
		Page<MenuResDTO> menuResDTOPage = menuService.findMenuListByStore(storeId, pageable, principal);
		return ResponseUtil.successResponse(SuccessCode.MENU_LIST_SUCCESS, menuResDTOPage);
	}

	@PreAuthorize("hasAnyRole('OWNER','MANAGER','MASTER')")
	@Operation(summary = "메뉴 수정 API", description = "메뉴를 수정한다.")
	@PatchMapping("/{menuId}")
	public ResponseEntity<ApiResponse<MenuResDTO>> updateMenu(
			@PathVariable("storeId") UUID storeId,
			@PathVariable("menuId") UUID menuId,
			@RequestBody MenuUpdateReqDTO menuUpdateReqDTO,
			@AuthenticationPrincipal UserDetailsImpl user) {
		MenuResDTO menuResDTO = menuService.updateMenu(storeId, menuId, menuUpdateReqDTO, user);
		return ResponseUtil.successResponse(SuccessCode.MENU_UPDATE_SUCCESS, menuResDTO);
	}

	@PreAuthorize("hasAnyRole('OWNER','MANAGER','MASTER')")
	@Operation(summary = "메뉴 삭제 API", description = "메뉴를 삭제한다.")
	@DeleteMapping("/{menuId}")
	public ResponseEntity<ApiResponse<Void>> deleteMenu(
			@PathVariable("storeId") UUID storeId,
			@PathVariable("menuId") UUID menuId,
			@AuthenticationPrincipal UserDetailsImpl user) {
		menuService.deleteMenu(storeId, menuId, user);
		return ResponseUtil.successResponse(SuccessCode.MENU_DELETE_SUCCESS);
	}

	@PreAuthorize("hasAnyRole('OWNER','MANAGER','MASTER')")
	@Operation(summary = "메뉴 숨김/복구 API", description = "메뉴 숨김 여부를 변경한다.")
	@PostMapping("/{menuId}/visibility")
	public ResponseEntity<ApiResponse<MenuResDTO>> updateMenuVisibility(
			@PathVariable("storeId") UUID storeId,
			@PathVariable("menuId") UUID menuId,
			@RequestBody MenuVisibilityReqDTO menuVisibilityReqDTO,
			@AuthenticationPrincipal UserDetailsImpl user) {
		MenuResDTO menuResDTO =
				menuService.updateMenuVisibility(storeId, menuId, menuVisibilityReqDTO.hidden(), user);
		return ResponseUtil.successResponse(SuccessCode.MENU_VISIBILITY_UPDATE_SUCCESS, menuResDTO);
	}

	@PreAuthorize("hasAnyRole('OWNER','MANAGER','MASTER')")
	@Operation(summary = "메뉴 품절/복구 API", description = "메뉴 품절 여부를 변경한다.")
	@PostMapping("/{menuId}/soldout")
	public ResponseEntity<ApiResponse<MenuResDTO>> updateMenuSoldOut(
			@PathVariable("storeId") UUID storeId,
			@PathVariable("menuId") UUID menuId,
			@RequestBody MenuSoldOutReqDTO menuSoldOutReqDTO,
			@AuthenticationPrincipal UserDetailsImpl user) {
		MenuResDTO menuResDTO =
				menuService.updateMenuSoldOut(storeId, menuId, menuSoldOutReqDTO.isSoldOut(), user);
		return ResponseUtil.successResponse(SuccessCode.MENU_SOLDOUT_UPDATE_SUCCESS, menuResDTO);
	}

	@Operation(summary = "메뉴 이미지 목록 조회 API", description = "메뉴의 이미지 목록을 조회한다.")
	@GetMapping("/{menuId}/images")
	public ResponseEntity<ApiResponse<List<FileInfoResDTO>>> getMenuImages(
			@PathVariable("storeId") UUID storeId,
			@PathVariable("menuId") UUID menuId,
			@AuthenticationPrincipal UserDetailsImpl user) {
		menuService.findMenuById(storeId, menuId, user);

		MenuResDTO menu = menuService.findMenuById(storeId, menuId, user);
		List<FileInfoResDTO> images = fileService.getList(menu.groupId(), "menu");
		return ResponseUtil.successResponse(SuccessCode.FILE_READ_SUCCESS, images);
	}

	@PreAuthorize("hasAnyRole('OWNER','MANAGER','MASTER')")
	@Operation(summary = "메뉴 이미지 삭제 API", description = "메뉴의 특정 이미지를 삭제한다.")
	@DeleteMapping("/{menuId}/images/{fileUuid}")
	public ResponseEntity<ApiResponse<Void>> deleteMenuImage(
			@PathVariable("storeId") UUID storeId,
			@PathVariable("menuId") UUID menuId,
			@PathVariable("fileUuid") String fileUuid,
			@AuthenticationPrincipal UserDetailsImpl user) {
		menuService.findMenuById(storeId, menuId, user);

		fileService.deleteFile(UUID.fromString(fileUuid));
		return ResponseUtil.successResponse(SuccessCode.FILE_DELETE_SUCCESS);
	}

	@PreAuthorize("hasAnyRole('OWNER','MANAGER','MASTER')")
	@Operation(summary = "메뉴 이미지 전체 삭제 API", description = "메뉴의 모든 이미지를 삭제한다.")
	@DeleteMapping("/{menuId}/images")
	public ResponseEntity<ApiResponse<Void>> deleteAllMenuImages(
			@PathVariable("storeId") UUID storeId,
			@PathVariable("menuId") UUID menuId,
			@AuthenticationPrincipal UserDetailsImpl user) {

		MenuResDTO menu = menuService.findMenuById(storeId, menuId, user);

		fileService.deleteFiles(menu.groupId(), "menu");
		return ResponseUtil.successResponse(SuccessCode.FILE_DELETE_SUCCESS);
	}
}

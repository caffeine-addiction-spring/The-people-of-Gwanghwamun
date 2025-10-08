package com.caffeine.gwanghwamun.domain.menu.controller;

import com.caffeine.gwanghwamun.common.response.ApiResponse;
import com.caffeine.gwanghwamun.common.response.ResponseUtil;
import com.caffeine.gwanghwamun.common.success.SuccessCode;
import com.caffeine.gwanghwamun.domain.menu.dto.request.MenuCreateReqDTO;
import com.caffeine.gwanghwamun.domain.menu.dto.request.MenuSoldOutReqDTO;
import com.caffeine.gwanghwamun.domain.menu.dto.request.MenuUpdateReqDTO;
import com.caffeine.gwanghwamun.domain.menu.dto.request.MenuVisibilityReqDTO;
import com.caffeine.gwanghwamun.domain.menu.dto.response.MenuResDTO;
import com.caffeine.gwanghwamun.domain.menu.service.MenuService;
import com.caffeine.gwanghwamun.domain.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/stores/{storeId}/menus")
@RequiredArgsConstructor
public class MenuController {

	private final MenuService menuService;

	@PreAuthorize("hasAnyRole('OWNER','MANAGER','MASTER')")
	@Operation(summary = "메뉴 생성", description = "메뉴를 생성한다.")
	@PostMapping
	public ResponseEntity<ApiResponse<MenuResDTO>> createMenu(
			@PathVariable("storeId") UUID storeId, @RequestBody MenuCreateReqDTO menuCreateReqDTO, @AuthenticationPrincipal User user) {
		MenuResDTO menuResDTO = menuService.saveMenu(storeId, menuCreateReqDTO);
		return ResponseUtil.successResponse(SuccessCode.MENU_SAVE_SUCCESS, menuResDTO);
	}

	@Operation(summary = "메뉴 상세 조회", description = "메뉴를 상세 조회할 수 있다.")
	@GetMapping("/{menuId}")
	public ResponseEntity<ApiResponse<MenuResDTO>> getMenu(
			@PathVariable("storeId") UUID storeId, @PathVariable("menuId") UUID menuId, @AuthenticationPrincipal User user) {
		MenuResDTO menuResDTO = menuService.findMenuById(storeId, menuId);
		return ResponseUtil.successResponse(SuccessCode.MENU_FIND_SUCCESS, menuResDTO);
	}

	@Operation(summary = "메뉴 목록 조회", description = "메뉴 목록을 조회할 수 있다.")
	@GetMapping
	public ResponseEntity<ApiResponse<Page<MenuResDTO>>> getMenuList(
			@PathVariable("storeId") UUID storeId,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size) {

		if (size != 10 && size != 30 && size != 50) {
			size = 10;
		}

		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
		Page<MenuResDTO> menuResDTOPage = menuService.findMenuListByStore(storeId, pageable);
		return ResponseUtil.successResponse(SuccessCode.MENU_LIST_SUCCESS, menuResDTOPage);
	}

	@PreAuthorize("hasAnyRole('OWNER','MANAGER','MASTER')")
	@Operation(summary = "메뉴 수정", description = "메뉴를 수정할 수 있다.")
	@PatchMapping("/{menuId}")
	public ResponseEntity<ApiResponse<MenuResDTO>> updateMenu(
			@PathVariable("storeId") UUID storeId,
			@PathVariable("menuId") UUID menuId,
			@RequestBody MenuUpdateReqDTO menuUpdateReqDTO,
			@AuthenticationPrincipal User user) {
		MenuResDTO menuResDTO = menuService.updateMenu(storeId, menuId, menuUpdateReqDTO);
		return ResponseUtil.successResponse(SuccessCode.MENU_UPDATE_SUCCESS, menuResDTO);
	}

	@PreAuthorize("hasAnyRole('OWNER','MANAGER','MASTER')")
	@Operation(summary = "메뉴 삭제", description = "메뉴를 삭제할 수 있다.")
	@DeleteMapping("/{menuId}")
	public ResponseEntity<ApiResponse<Void>> deleteMenu(
			@PathVariable("storeId") UUID storeId, @PathVariable("menuId") UUID menuId, @AuthenticationPrincipal User user) {
		menuService.deleteMenu(storeId, menuId);
		return ResponseUtil.successResponse(SuccessCode.MENU_DELETE_SUCCESS);
	}

	@PreAuthorize("hasAnyRole('OWNER','MANAGER','MASTER')")
	@Operation(summary = "메뉴 숨김", description = "메뉴를 숨길 수 있다.")
	@PostMapping("/{menuId}/visibility")
	public ResponseEntity<ApiResponse<MenuResDTO>> updateMenuVisibility(
			@PathVariable("storeId") UUID storeId,
			@PathVariable("menuId") UUID menuId,
			@RequestBody MenuVisibilityReqDTO menuVisibilityReqDTO,
			@AuthenticationPrincipal User user) {
		MenuResDTO menuResDTO =
				menuService.updateMenuVisibility(storeId, menuId, menuVisibilityReqDTO.hidden());
		return ResponseUtil.successResponse(SuccessCode.MENU_VISIBILITY_UPDATE_SUCCESS, menuResDTO);
	}

	@PreAuthorize("hasAnyRole('OWNER','MANAGER','MASTER')")
	@Operation(summary = "메뉴 품절", description = "메뉴 품절 여부를 표시한다.")
	@PostMapping("/{menuId}/soldout")
	public ResponseEntity<ApiResponse<MenuResDTO>> updateMenuSoldOut(
			@PathVariable("storeId") UUID storeId,
			@PathVariable("menuId") UUID menuId,
			@RequestBody MenuSoldOutReqDTO menuSoldOutReqDTO,
			@AuthenticationPrincipal User user) {
		MenuResDTO menuResDTO =
				menuService.updateMenuSoldOut(storeId, menuId, menuSoldOutReqDTO.isSoldOut());
		return ResponseUtil.successResponse(SuccessCode.MENU_SOLDOUT_UPDATE_SUCCESS, menuResDTO);
	}
}

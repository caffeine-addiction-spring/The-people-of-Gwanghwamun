package com.caffeine.gwanghwamun.domain.menu.controller;

import com.caffeine.gwanghwamun.common.response.ApiResponse;
import com.caffeine.gwanghwamun.common.response.ResponseUtil;
import com.caffeine.gwanghwamun.common.security.model.UserDetailsImpl;
import com.caffeine.gwanghwamun.common.success.SuccessCode;
import com.caffeine.gwanghwamun.domain.menu.dto.request.MenuOptionCreateReqDTO;
import com.caffeine.gwanghwamun.domain.menu.dto.request.MenuOptionSoldOutReqDTO;
import com.caffeine.gwanghwamun.domain.menu.dto.request.MenuOptionUpdateReqDTO;
import com.caffeine.gwanghwamun.domain.menu.dto.request.MenuOptionVisibilityReqDTO;
import com.caffeine.gwanghwamun.domain.menu.dto.response.MenuOptionResDTO;
import com.caffeine.gwanghwamun.domain.menu.service.MenuOptionService;
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
@RequestMapping("/v1/stores/{storeId}/menus/{menuId}/options")
@RequiredArgsConstructor
public class MenuOptionController {

	private final MenuOptionService menuOptionService;

	@PreAuthorize("hasAnyRole('OWNER','MANAGER','MASTER')")
	@Operation(summary = "메뉴 옵션 생성 API", description = "메뉴 옵션을 생성한다.")
	@PostMapping
	public ResponseEntity<ApiResponse<MenuOptionResDTO>> createOption(
			@PathVariable("storeId") UUID storeId,
			@PathVariable("menuId") UUID menuId,
			@RequestBody MenuOptionCreateReqDTO req,
			@AuthenticationPrincipal UserDetailsImpl user) {
		MenuOptionResDTO res = menuOptionService.saveOption(storeId, menuId, req, user);
		return ResponseUtil.successResponse(SuccessCode.MENU_OPTION_SAVE_SUCCESS, res);
	}

	@Operation(summary = "메뉴 옵션 목록 조회 API", description = "메뉴 옵션 목록을 조회한다.")
	@GetMapping
	public ResponseEntity<ApiResponse<Page<MenuOptionResDTO>>> getOptionList(
			@PathVariable("storeId") UUID storeId,
			@PathVariable("menuId") UUID menuId,
			@RequestParam(name = "includeHidden", required = false) Boolean includeHidden,
			@RequestParam(name = "soldOut", required = false) Boolean soldOut,
			@RequestParam(name = "optionName", required = false) String optionName,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size,
			@RequestParam(name = "sortBy", defaultValue = "createAt") String sortBy,
			@RequestParam(name = "direction", defaultValue = "desc") String direction,
			@AuthenticationPrincipal UserDetailsImpl principal) {

		if (size != 10 && size != 30 && size != 50) size = 10;
		Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
		Page<MenuOptionResDTO> res =
				menuOptionService.findOptionList(
						storeId, menuId, includeHidden, soldOut, optionName, pageable, principal);
		return ResponseUtil.successResponse(SuccessCode.MENU_OPTION_LIST_SUCCESS, res);
	}

	@Operation(summary = "메뉴 옵션 상세 조회 API", description = "메뉴 옵션을 상세 조회한다.")
	@GetMapping("/{optionId}")
	public ResponseEntity<ApiResponse<MenuOptionResDTO>> getOption(
			@PathVariable("storeId") UUID storeId,
			@PathVariable("menuId") UUID menuId,
			@PathVariable("optionId") UUID optionId,
			@AuthenticationPrincipal UserDetailsImpl user) {
		MenuOptionResDTO res = menuOptionService.findOption(storeId, menuId, optionId, user);
		return ResponseUtil.successResponse(SuccessCode.MENU_OPTION_FIND_SUCCESS, res);
	}

	@PreAuthorize("hasAnyRole('OWNER','MANAGER','MASTER')")
	@Operation(summary = "메뉴 옵션 수정 API", description = "메뉴 옵션을 수정한다.")
	@PatchMapping("/{optionId}")
	public ResponseEntity<ApiResponse<MenuOptionResDTO>> updateOption(
			@PathVariable("storeId") UUID storeId,
			@PathVariable("menuId") UUID menuId,
			@PathVariable("optionId") UUID optionId,
			@RequestBody MenuOptionUpdateReqDTO req,
			@AuthenticationPrincipal UserDetailsImpl user) {
		MenuOptionResDTO res = menuOptionService.updateOption(storeId, menuId, optionId, req, user);
		return ResponseUtil.successResponse(SuccessCode.MENU_OPTION_UPDATE_SUCCESS, res);
	}

	@PreAuthorize("hasAnyRole('OWNER','MANAGER','MASTER')")
	@Operation(summary = "메뉴 옵션 삭제 API", description = "메뉴 옵션을 삭제한다.")
	@DeleteMapping("/{optionId}")
	public ResponseEntity<ApiResponse<Void>> deleteOption(
			@PathVariable("storeId") UUID storeId,
			@PathVariable("menuId") UUID menuId,
			@PathVariable("optionId") UUID optionId,
			@AuthenticationPrincipal UserDetailsImpl user) {
		menuOptionService.deleteOption(storeId, menuId, optionId, null, user);
		return ResponseUtil.successResponse(SuccessCode.MENU_OPTION_DELETE_SUCCESS);
	}

	@PreAuthorize("hasAnyRole('OWNER','MANAGER','MASTER')")
	@Operation(summary = "메뉴 옵션 숨김/복구 API", description = "메뉴 옵션 숨김 여부를 변경한다.")
	@PostMapping("/{optionId}/visibility")
	public ResponseEntity<ApiResponse<MenuOptionResDTO>> updateOptionVisibility(
			@PathVariable("storeId") UUID storeId,
			@PathVariable("menuId") UUID menuId,
			@PathVariable("optionId") UUID optionId,
			@RequestBody MenuOptionVisibilityReqDTO req,
			@AuthenticationPrincipal UserDetailsImpl user) {
		MenuOptionResDTO res =
				menuOptionService.updateOptionVisibility(storeId, menuId, optionId, req.hidden(), user);
		return ResponseUtil.successResponse(SuccessCode.MENU_OPTION_VISIBILITY_UPDATE_SUCCESS, res);
	}

	@PreAuthorize("hasAnyRole('OWNER','MANAGER','MASTER')")
	@Operation(summary = "메뉴 옵션 품절/복구 API", description = "메뉴 옵션 품절 여부를 변경한다.")
	@PostMapping("/{optionId}/soldout")
	public ResponseEntity<ApiResponse<MenuOptionResDTO>> updateSoldOut(
			@PathVariable("storeId") UUID storeId,
			@PathVariable("menuId") UUID menuId,
			@PathVariable("optionId") UUID optionId,
			@RequestBody MenuOptionSoldOutReqDTO req,
			@AuthenticationPrincipal UserDetailsImpl user) {
		MenuOptionResDTO res = menuOptionService.updateSoldOut(storeId, menuId, optionId, req, user);
		return ResponseUtil.successResponse(SuccessCode.MENU_OPTION_SOLDOUT_UPDATE_SUCCESS, res);
	}
}

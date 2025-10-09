package com.caffeine.gwanghwamun.domain.menuoption.controller;

import com.caffeine.gwanghwamun.common.response.ApiResponse;
import com.caffeine.gwanghwamun.common.response.ResponseUtil;
import com.caffeine.gwanghwamun.common.success.SuccessCode;
import com.caffeine.gwanghwamun.domain.menuoption.dto.request.MenuOptionCreateReqDTO;
import com.caffeine.gwanghwamun.domain.menuoption.dto.request.MenuOptionSoldOutReqDTO;
import com.caffeine.gwanghwamun.domain.menuoption.dto.request.MenuOptionUpdateReqDTO;
import com.caffeine.gwanghwamun.domain.menuoption.dto.request.MenuOptionVisibilityReqDTO;
import com.caffeine.gwanghwamun.domain.menuoption.dto.response.MenuOptionResDTO;
import com.caffeine.gwanghwamun.domain.menuoption.service.MenuOptionService;
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
@RequestMapping("/v1/stores/{storeId}/menus/{menuId}/options")
@RequiredArgsConstructor
public class MenuOptionController {

	private final MenuOptionService menuOptionService;

	@PreAuthorize("hasAnyRole('OWNER','MANAGER','MASTER')")
	@Operation(summary = "옵션 등록", description = "메뉴 옵션을 등록한다.")
	@PostMapping
	public ResponseEntity<ApiResponse<MenuOptionResDTO>> createOption(
			@PathVariable UUID storeId,
			@PathVariable UUID menuId,
			@RequestBody MenuOptionCreateReqDTO req,
			@AuthenticationPrincipal User user) {
		MenuOptionResDTO res = menuOptionService.saveOption(storeId, menuId, req);
		return ResponseUtil.successResponse(SuccessCode.MENU_OPTION_SAVE_SUCCESS, res);
	}

	@Operation(summary = "옵션 목록 조회", description = "메뉴 옵션 목록을 조회한다.")
	@GetMapping
	public ResponseEntity<ApiResponse<Page<MenuOptionResDTO>>> getOptionList(
			@PathVariable UUID storeId,
			@PathVariable UUID menuId,
			@RequestParam(required = false) Boolean includeHidden,
			@RequestParam(required = false) Boolean soldOut,
			@RequestParam(required = false) String optionName,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@AuthenticationPrincipal User user) {

		if (size != 10 && size != 30 && size != 50) {
			size = 10;
		}
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
		Page<MenuOptionResDTO> res =
				menuOptionService.findOptionList(
						storeId, menuId, includeHidden, soldOut, optionName, pageable);
		return ResponseUtil.successResponse(SuccessCode.MENU_OPTION_LIST_SUCCESS, res);
	}

	@Operation(summary = "옵션 상세 조회", description = "메뉴 옵션을 상세 조회한다.")
	@GetMapping("/{optionId}")
	public ResponseEntity<ApiResponse<MenuOptionResDTO>> getOption(
			@PathVariable UUID storeId, @PathVariable UUID menuId, @PathVariable UUID optionId, @AuthenticationPrincipal User user) {
		MenuOptionResDTO res = menuOptionService.findOption(storeId, menuId, optionId);
		return ResponseUtil.successResponse(SuccessCode.MENU_OPTION_FIND_SUCCESS, res);
	}

	@PreAuthorize("hasAnyRole('OWNER','MANAGER','MASTER')")
	@Operation(summary = "옵션 수정", description = "메뉴 옵션을 수정한다.")
	@PatchMapping("/{optionId}")
	public ResponseEntity<ApiResponse<MenuOptionResDTO>> updateOption(
			@PathVariable UUID storeId,
			@PathVariable UUID menuId,
			@PathVariable UUID optionId,
			@RequestBody MenuOptionUpdateReqDTO req,
			@AuthenticationPrincipal User user) {
		MenuOptionResDTO res = menuOptionService.updateOption(storeId, menuId, optionId, req);
		return ResponseUtil.successResponse(SuccessCode.MENU_OPTION_UPDATE_SUCCESS, res);
	}

	@PreAuthorize("hasAnyRole('OWNER','MANAGER','MASTER')")
	@Operation(summary = "옵션 삭제", description = "메뉴 옵션을 삭제한다.")
	@DeleteMapping("/{optionId}")
	public ResponseEntity<ApiResponse<Void>> deleteOption(
			@PathVariable UUID storeId, @PathVariable UUID menuId, @PathVariable UUID optionId, @AuthenticationPrincipal User user) {
		menuOptionService.deleteOption(storeId, menuId, optionId, null);
		return ResponseUtil.successResponse(SuccessCode.MENU_OPTION_DELETE_SUCCESS);
	}

	@PreAuthorize("hasAnyRole('OWNER','MANAGER','MASTER')")
	@Operation(summary = "옵션 숨김/복구", description = "옵션을 숨기거나 다시 보이게 할 수 있다.")
	@PostMapping("/{optionId}/visibility")
	public ResponseEntity<ApiResponse<MenuOptionResDTO>> updateOptionVisibility(
			@PathVariable UUID storeId,
			@PathVariable UUID menuId,
			@PathVariable UUID optionId,
			@RequestBody MenuOptionVisibilityReqDTO req,
			@AuthenticationPrincipal User user) {
		MenuOptionResDTO res = menuOptionService.updateOptionVisibility(storeId, menuId, optionId, req.hidden());
		return ResponseUtil.successResponse(SuccessCode.MENU_OPTION_VISIBILITY_UPDATE_SUCCESS, res);
	}

	@PreAuthorize("hasAnyRole('OWNER','MANAGER','MASTER')")
	@Operation(summary = "옵션 품절/복구", description = "메뉴 옵션 품절 여부를 변경한다.")
	@PostMapping("/{optionId}/soldout")
	public ResponseEntity<ApiResponse<MenuOptionResDTO>> updateSoldOut(
			@PathVariable UUID storeId,
			@PathVariable UUID menuId,
			@PathVariable UUID optionId,
			@RequestBody MenuOptionSoldOutReqDTO req,
			@AuthenticationPrincipal User user) {
		MenuOptionResDTO res = menuOptionService.updateSoldOut(storeId, menuId, optionId, req);
		return ResponseUtil.successResponse(SuccessCode.MENU_OPTION_SOLDOUT_UPDATE_SUCCESS, res);
	}
}

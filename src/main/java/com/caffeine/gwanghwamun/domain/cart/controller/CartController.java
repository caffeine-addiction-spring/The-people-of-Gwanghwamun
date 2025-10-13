package com.caffeine.gwanghwamun.domain.cart.controller;

import com.caffeine.gwanghwamun.common.response.ApiResponse;
import com.caffeine.gwanghwamun.common.response.ResponseUtil;
import com.caffeine.gwanghwamun.common.success.SuccessCode;
import com.caffeine.gwanghwamun.domain.cart.dto.SaveCartReqDTO;
import com.caffeine.gwanghwamun.domain.cart.dto.SaveCartResDTO;
import com.caffeine.gwanghwamun.domain.cart.service.CartService;
import com.caffeine.gwanghwamun.domain.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/cart")
@RequiredArgsConstructor
public class CartController {

	private final CartService cartService;

	@PreAuthorize("hasRole('CUSTOMER')")
	@Operation(summary = "장바구니 담기", description = "메뉴를 장바구니에 담는다.")
	@PostMapping
	public ResponseEntity<ApiResponse<SaveCartResDTO>> addCartItem(
			@RequestBody SaveCartReqDTO req, @AuthenticationPrincipal User user) {

		SaveCartResDTO cart = cartService.saveCart(user.getUserId(), req);
		return ResponseUtil.successResponse(SuccessCode.CART_SAVE_SUCCESS, cart);
	}

	// @PreAuthorize("hasRole('CUSTOMER')")
	// @Operation(summary = "장바구니 조회", description = "장바구니 목록을 조회한다.")
	// @GetMapping
	// public ResponseEntity<ApiResponse<List<CartResDTO>>> getCartItems(
	//         @AuthenticationPrincipal User user) {
	//
	//     List<CartResDTO> cartItems = cartService.getCartItems(user);
	//     return ResponseUtil.successResponse(SuccessCode.CART_LIST_SUCCESS, cartItems);
	// }
	//
	// @PreAuthorize("hasRole('CUSTOMER')")
	// @Operation(summary = "장바구니 항목 수정", description = "장바구니에 담긴 메뉴의 수량을 수정한다.")
	// @PutMapping("/{cartId}")
	// public ResponseEntity<ApiResponse<CartResDTO>> updateCartItem(
	//         @PathVariable("cartId") UUID cartId,
	//         @RequestBody CartUpdateReqDTO cartUpdateReqDTO,
	//         @AuthenticationPrincipal User user) {
	//
	//     CartResDTO updatedCart = cartService.updateCartItem(user, cartId, cartUpdateReqDTO);
	//     return ResponseUtil.successResponse(SuccessCode.CART_UPDATE_SUCCESS, updatedCart);
	// }
	//
	// @PreAuthorize("hasRole('CUSTOMER')")
	// @Operation(summary = "장바구니 항목 삭제", description = "장바구니에서 항목을 삭제한다.")
	// @DeleteMapping("/{cartId}")
	// public ResponseEntity<ApiResponse<Void>> deleteCartItem(
	//         @PathVariable("cartId") UUID cartId,
	//         @AuthenticationPrincipal User user) {
	//
	//     cartService.deleteCartItem(user, cartId);
	//     return ResponseUtil.successResponse(SuccessCode.CART_DELETE_SUCCESS);
	// }
}

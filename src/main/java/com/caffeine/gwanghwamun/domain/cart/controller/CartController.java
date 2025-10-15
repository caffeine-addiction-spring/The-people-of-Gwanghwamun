package com.caffeine.gwanghwamun.domain.cart.controller;

import com.caffeine.gwanghwamun.common.response.ApiResponse;
import com.caffeine.gwanghwamun.common.response.ResponseUtil;
import com.caffeine.gwanghwamun.common.security.model.UserDetailsImpl;
import com.caffeine.gwanghwamun.common.success.SuccessCode;
import com.caffeine.gwanghwamun.domain.cart.dto.CartResDTO;
import com.caffeine.gwanghwamun.domain.cart.dto.SaveCartReqDTO;
import com.caffeine.gwanghwamun.domain.cart.dto.SaveCartResDTO;
import com.caffeine.gwanghwamun.domain.cart.dto.UpdateCartReqDTO;
import com.caffeine.gwanghwamun.domain.cart.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/cart")
@RequiredArgsConstructor
public class CartController {

  private final CartService cartService;

  @PreAuthorize("hasRole('CUSTOMER')")
  @Operation(summary = "장바구니 담기", description = "메뉴를 장바구니에 담는다.")
  @PostMapping
  public ResponseEntity<ApiResponse<SaveCartResDTO>> saveCart(
      @RequestBody SaveCartReqDTO req, @AuthenticationPrincipal UserDetailsImpl user) {

    SaveCartResDTO cart = cartService.saveCart(user.getUser().getUserId(), req);
    return ResponseUtil.successResponse(SuccessCode.CART_SAVE_SUCCESS, cart);
  }

  @PreAuthorize("hasRole('CUSTOMER')")
  @Operation(summary = "장바구니 조회", description = "장바구니 목록을 조회한다.")
  @GetMapping
  public ResponseEntity<ApiResponse<List<CartResDTO>>> findCartList(
      @AuthenticationPrincipal UserDetailsImpl user) {

    List<CartResDTO> cartItems = cartService.findCartList(user.getUser());
    return ResponseUtil.successResponse(SuccessCode.CART_LIST_SUCCESS, cartItems);
  }

  @PreAuthorize("hasRole('CUSTOMER')")
  @Operation(summary = "장바구니 항목 수정", description = "장바구니에 담긴 메뉴의 옵션과 수량을 수정한다.")
  @PutMapping("/{cartId}")
  public ResponseEntity<ApiResponse<CartResDTO>> updateCart(
      @PathVariable("cartId") UUID cartId,
      @RequestBody UpdateCartReqDTO cartUpdateReqDTO,
      @AuthenticationPrincipal UserDetailsImpl user) {

    CartResDTO updatedCart = cartService.updateCart(user.getUser(), cartId, cartUpdateReqDTO);
    return ResponseUtil.successResponse(SuccessCode.CART_UPDATE_SUCCESS, updatedCart);
  }

  @PreAuthorize("hasAnyRole('CUSTOMER', 'MASTER')")
  @Operation(summary = "장바구니 항목 삭제", description = "장바구니에서 항목을 삭제한다.")
  @DeleteMapping("/{cartId}")
  public ResponseEntity<ApiResponse<Void>> deleteCart(
      @PathVariable("cartId") UUID cartId, @AuthenticationPrincipal UserDetailsImpl user) {

    cartService.deleteCart(user.getUser(), cartId);
    return ResponseUtil.successResponse(SuccessCode.CART_DELETE_SUCCESS);
  }
}

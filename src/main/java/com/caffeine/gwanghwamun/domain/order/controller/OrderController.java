package com.caffeine.gwanghwamun.domain.order.controller;

import com.caffeine.gwanghwamun.common.response.ApiResponse;
import com.caffeine.gwanghwamun.common.response.ResponseUtil;
import com.caffeine.gwanghwamun.common.security.model.UserDetailsImpl;
import com.caffeine.gwanghwamun.common.success.SuccessCode;
import com.caffeine.gwanghwamun.domain.order.dto.*;
import com.caffeine.gwanghwamun.domain.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/order")
public class OrderController {

  private final OrderService orderService;

  @PreAuthorize("hasAnyRole('CUSTOMER','MANAGER','MASTER')")
  @Operation(summary = "주문 생성", description = "사용자가 주문을 생성한다.")
  @PostMapping
  public ResponseEntity<ApiResponse<SaveOrderResDTO>> saveOrder(
      @RequestBody SaveOrderReqDTO req, @AuthenticationPrincipal UserDetailsImpl user) {

    SaveOrderResDTO res = orderService.saveOrder(req, user.getUser().getUserId());
    return ResponseUtil.successResponse(SuccessCode.ORDER_SAVE_SUCCESS, res);
  }

  @PreAuthorize("hasAnyRole('CUSTOMER','OWNER','MANAGER','MASTER')")
  @Operation(summary = "주문 목록 조회", description = "주문 목록을 조회한다.")
  @GetMapping
  public ResponseEntity<ApiResponse<Page<OrderListResDTO>>> findOrderList(
      @AuthenticationPrincipal UserDetailsImpl user,
      @PageableDefault(page = 0, size = 10, sort = "createAt", direction = Sort.Direction.DESC)
      Pageable pageable) {

    Page<OrderListResDTO> orderPage =
        orderService.findOrderList(user.getUser().getUserId(), pageable);
    return ResponseUtil.successResponse(SuccessCode.ORDER_LIST_SUCCESS, orderPage);
  }

  @PreAuthorize("hasAnyRole('CUSTOMER','OWNER','MANAGER','MASTER')")
  @Operation(summary = "주문 상세 조회 API", description = "주문 상세 정보를 조회한다.")
  @GetMapping("/{orderId}")
  public ResponseEntity<ApiResponse<OrderResDTO>> findOrder(
      @PathVariable UUID orderId, @AuthenticationPrincipal UserDetailsImpl user) {

    OrderResDTO orderDetail = orderService.findOrder(user.getUser().getUserId(), orderId);
    return ResponseUtil.successResponse(SuccessCode.ORDER_FIND_SUCCESS, orderDetail);
  }

  @PreAuthorize("hasAnyRole('CUSTOMER','OWNER','MANAGER','MASTER')")
  @Operation(summary = "주문 취소 API", description = "주문을 취소한다. 주문 후 5분 이내만 가능하다.")
  @PutMapping("/{orderId}/cancel")
  public ResponseEntity<ApiResponse<OrderStatusResDTO>> cancelOrder(
      @PathVariable UUID orderId,
      @RequestBody OrderCancelReqDTO req,
      @AuthenticationPrincipal UserDetailsImpl user) {

    OrderStatusResDTO canceledOrder = orderService.cancelOrder(user.getUser(), orderId, req);
    return ResponseUtil.successResponse(SuccessCode.ORDER_CANCEL_SUCCESS, canceledOrder);
  }

  @PreAuthorize("hasAnyRole('OWNER','MANAGER','MASTER')")
  @Operation(summary = "주문 수락 API", description = "가게 주문을 수락한다.")
  @PutMapping("/{orderId}/accept")
  public ResponseEntity<ApiResponse<OrderStatusResDTO>> acceptOrder(
      @PathVariable UUID orderId, @AuthenticationPrincipal UserDetailsImpl user) {

    OrderStatusResDTO orderRes = orderService.acceptOrder(user.getUser(), orderId);
    return ResponseUtil.successResponse(SuccessCode.ORDER_ACCEPT_SUCCESS, orderRes);
  }

  @PreAuthorize("hasAnyRole('OWNER','MANAGER','MASTER')")
  @Operation(summary = "주문 거절 API", description = "가게 주문을 거절한다.")
  @PutMapping("/{orderId}/reject")
  public ResponseEntity<ApiResponse<OrderStatusResDTO>> rejectOrder(
      @PathVariable UUID orderId, @AuthenticationPrincipal UserDetailsImpl user) {

    OrderStatusResDTO orderRes = orderService.rejectOrder(user.getUser(), orderId);
    return ResponseUtil.successResponse(SuccessCode.ORDER_REJECT_SUCCESS, orderRes);
  }

  @PreAuthorize("hasAnyRole('OWNER','MANAGER','MASTER')")
  @Operation(summary = "조리 완료 API", description = "주문 조리를 완료 처리한다.")
  @PutMapping("/{orderId}/cook-complete")
  public ResponseEntity<ApiResponse<OrderStatusResDTO>> completeCooking(
      @PathVariable UUID orderId, @AuthenticationPrincipal UserDetailsImpl user) {

    OrderStatusResDTO orderRes = orderService.completeCooking(user.getUser(), orderId);
    return ResponseUtil.successResponse(SuccessCode.ORDER_COOK_COMPLETE_SUCCESS, orderRes);
  }

  @PreAuthorize("hasAnyRole('OWNER','MANAGER','MASTER')")
  @Operation(summary = "배달 완료 API", description = "주문 배달 완료 처리한다.")
  @PutMapping("/{orderId}/delivery-complete")
  public ResponseEntity<ApiResponse<OrderStatusResDTO>> completeDelivery(
      @PathVariable UUID orderId, @AuthenticationPrincipal UserDetailsImpl user) {

    OrderStatusResDTO orderRes = orderService.completeDelivery(user.getUser(), orderId);
    return ResponseUtil.successResponse(SuccessCode.ORDER_DELIVERY_COMPLETE_SUCCESS, orderRes);
  }
}

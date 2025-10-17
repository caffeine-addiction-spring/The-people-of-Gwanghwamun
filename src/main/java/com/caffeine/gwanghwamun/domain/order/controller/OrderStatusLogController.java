package com.caffeine.gwanghwamun.domain.order.controller;

import com.caffeine.gwanghwamun.common.response.ApiResponse;
import com.caffeine.gwanghwamun.common.response.ResponseUtil;
import com.caffeine.gwanghwamun.common.success.SuccessCode;
import com.caffeine.gwanghwamun.domain.order.dto.OrderStatusLogResDTO;
import com.caffeine.gwanghwamun.domain.order.service.OrderStatusLogService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@PreAuthorize("hasAnyRole('MANAGER','MASTER')")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/order/status-log")
public class OrderStatusLogController {

  private final OrderStatusLogService orderStatusLogService;

  @Operation(summary = "전체 로그 조회 API", description = "모든 주문 상태 로그를 조회한다.")
  @GetMapping
  public ResponseEntity<ApiResponse<Page<OrderStatusLogResDTO>>> getAllLogs(
      @PageableDefault(page = 0, size = 10, sort = "createAt", direction = Sort.Direction.DESC)
      Pageable pageable) {
    Page<OrderStatusLogResDTO> logList = orderStatusLogService.findLogList(pageable);
    return ResponseUtil.successResponse(SuccessCode.ORDER_LOG_LIST_SUCCESS, logList);
  }

  @Operation(summary = "회원별 로그 조회 API", description = "회원별 주문 상태 로그를 조회한다.")
  @GetMapping("/user/{userId}")
  public ResponseEntity<ApiResponse<Page<OrderStatusLogResDTO>>> getLogsByUser(
      @PathVariable Long userId,
      @PageableDefault(page = 0, size = 10, sort = "createAt", direction = Sort.Direction.DESC)
      Pageable pageable) {
    Page<OrderStatusLogResDTO> logList = orderStatusLogService.findLogByUser(userId, pageable);
    return ResponseUtil.successResponse(SuccessCode.ORDER_LOG_LIST_SUCCESS, logList);
  }

  @Operation(summary = "가게별 로그 조회 API", description = "가게별 주문 상태 로그를 조회한다.")
  @GetMapping("/store/{storeId}")
  public ResponseEntity<ApiResponse<Page<OrderStatusLogResDTO>>> getLogsByStore(
      @PathVariable UUID storeId,
      @PageableDefault(page = 0, size = 10, sort = "createAt", direction = Sort.Direction.DESC)
      Pageable pageable) {
    Page<OrderStatusLogResDTO> logList = orderStatusLogService.findLogByStore(storeId, pageable);
    return ResponseUtil.successResponse(SuccessCode.ORDER_LOG_LIST_SUCCESS, logList);
  }

  @Operation(summary = "주문별 로그 조회 API", description = "주문별 상태 로그를 조회한다.")
  @GetMapping("/order/{orderId}")
  public ResponseEntity<ApiResponse<Page<OrderStatusLogResDTO>>> getLogsByOrder(
      @PathVariable UUID orderId,
      @PageableDefault(page = 0, size = 10, sort = "createAt", direction = Sort.Direction.DESC)
      Pageable pageable) {
    Page<OrderStatusLogResDTO> logList = orderStatusLogService.findLogByOrder(orderId, pageable);
    return ResponseUtil.successResponse(SuccessCode.ORDER_LOG_LIST_SUCCESS, logList);
  }

  @Operation(summary = "개별 로그 조회 API", description = "특정 로그 ID로 조회한다.")
  @GetMapping("/{logId}")
  public ResponseEntity<ApiResponse<OrderStatusLogResDTO>> getLogById(@PathVariable UUID logId) {
    OrderStatusLogResDTO logList = orderStatusLogService.findLogById(logId);
    return ResponseUtil.successResponse(SuccessCode.ORDER_LOG_SUCCESS, logList);
  }
}

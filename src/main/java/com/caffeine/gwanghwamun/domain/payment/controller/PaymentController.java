package com.caffeine.gwanghwamun.domain.payment.controller;

import com.caffeine.gwanghwamun.common.response.ApiResponse;
import com.caffeine.gwanghwamun.common.response.ResponseUtil;
import com.caffeine.gwanghwamun.common.security.model.UserDetailsImpl;
import com.caffeine.gwanghwamun.common.success.SuccessCode;
import com.caffeine.gwanghwamun.domain.payment.dto.PaymentCreateReqDTO;
import com.caffeine.gwanghwamun.domain.payment.dto.PaymentDetailResDTO;
import com.caffeine.gwanghwamun.domain.payment.dto.PaymentResDTO;
import com.caffeine.gwanghwamun.domain.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

	private final PaymentService paymentService;

	@Operation(summary = "결제 생성 API", description = "새로운 결제를 생성한다.")
	@PostMapping("/orders/{orderId}")
	public ResponseEntity<ApiResponse<PaymentResDTO>> createPayment(
			@PathVariable("orderId") UUID orderId,
			@Valid @RequestBody PaymentCreateReqDTO paymentCreateReqDTO,
			@AuthenticationPrincipal UserDetailsImpl userDetails) {
		PaymentResDTO paymentResDTO =
				paymentService.createPayment(
						orderId, paymentCreateReqDTO, userDetails.getUser().getUserId());
		return ResponseUtil.successResponse(SuccessCode.PAYMENT_CREATE_SUCCESS, paymentResDTO);
	}

	@PreAuthorize("hasAnyRole('CUSTOMER','OWNER','MANAGER','MASTER')")
	@Operation(summary = "결제 단건 조회 API", description = "결제 내역 상세를 조회 한다.")
	@GetMapping("/{paymentId}")
	public ResponseEntity<ApiResponse<PaymentResDTO>> findPayment(
			@PathVariable("paymentId") UUID paymentId,
			@AuthenticationPrincipal UserDetailsImpl userDetails) {
		PaymentResDTO paymentResDTO =
				paymentService.findPaymentById(paymentId, userDetails.getUser().getUserId());
		return ResponseUtil.successResponse(SuccessCode.PAYMENT_FIND_SUCCESS, paymentResDTO);
	}

	@Operation(summary = "결제 내역 조회 API", description = "회원) 결제 내역 리스트를 조회 한다.")
	@GetMapping
	public ResponseEntity<ApiResponse<Page<PaymentResDTO>>> getPaymentList(
			Pageable pageable, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		Page<PaymentResDTO> paymentList =
				paymentService.getPaymentList(userDetails.getUser().getUserId(), pageable);
		return ResponseUtil.successResponse(SuccessCode.PAYMENT_LIST_FIND_SUCCESS, paymentList);
	}

	@PreAuthorize("hasAnyRole('OWNER')")
	@Operation(summary = "가게 결제 내역 조회 API", description = "가게) 결제 내역 리스트를 조회 한다.")
	@GetMapping("/store/{storeId}")
	public ResponseEntity<ApiResponse<Page<PaymentDetailResDTO>>> getPaymentListByStore(
			Pageable pageable,
			@PathVariable("storeId") UUID storeId,
			@AuthenticationPrincipal UserDetailsImpl userDetails) {
		Page<PaymentDetailResDTO> storePaymentList =
				paymentService.findPaymentListByStoreId(
						storeId, userDetails.getUser().getUserId(), pageable);
		return ResponseUtil.successResponse(
				SuccessCode.PAYMENT_STORE_LIST_FIND_SUCCESS, storePaymentList);
	}
}

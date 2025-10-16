package com.caffeine.gwanghwamun.domain.payment.service;

import com.caffeine.gwanghwamun.common.exception.CustomException;
import com.caffeine.gwanghwamun.common.exception.ErrorCode;
import com.caffeine.gwanghwamun.domain.order.entity.Order;
import com.caffeine.gwanghwamun.domain.order.repository.OrderRepository;
import com.caffeine.gwanghwamun.domain.payment.dto.PaymentCreateReqDTO;
import com.caffeine.gwanghwamun.domain.payment.dto.PaymentDetailResDTO;
import com.caffeine.gwanghwamun.domain.payment.dto.PaymentResDTO;
import com.caffeine.gwanghwamun.domain.payment.entity.Payment;
import com.caffeine.gwanghwamun.domain.payment.entity.PaymentStatus;
import com.caffeine.gwanghwamun.domain.payment.repository.PaymentRepository;
import com.caffeine.gwanghwamun.domain.store.entity.Store;
import com.caffeine.gwanghwamun.domain.store.repository.StoreRepository;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
@Service
public class PaymentService {

	private final OrderRepository orderRepository;
	private final PaymentRepository paymentRepository;
	private final StoreRepository storeRepository;

	@Transactional
	public PaymentResDTO createPayment(
			UUID orderId, @Valid PaymentCreateReqDTO paymentCreateReqDTO, Long userId) {

		Order order =
				orderRepository
						.findById(orderId)
						.orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

		if (!order.getUser().getUserId().equals(userId)) {
			throw new CustomException(ErrorCode.PAYMENT_UNAUTHORIZED);
		}

		if (paymentRepository.existsByOrderId(orderId)) {
			throw new CustomException(ErrorCode.PAYMENT_ALREADY_EXIST);
		}

		if (!order.getTotalPrice().equals(paymentCreateReqDTO.amount())) {
			throw new CustomException(ErrorCode.PAYMENT_INVALID_AMOUNT);
		}

		Payment payment =
				Payment.builder()
						.userId(userId)
						.orderId(orderId)
						.storeId(order.getStore().getStoreId())
						.amount(paymentCreateReqDTO.amount())
						.paymentStatus(PaymentStatus.PENDING)
						.build();

		Payment savedPayment = paymentRepository.save(payment);
		savedPayment.setApprovedAt();

		return PaymentResDTO.from(savedPayment);
	}

	@Transactional
	public PaymentResDTO findPaymentById(UUID paymentId, Long userId) {
		Payment existPayment =
				paymentRepository
						.findById(paymentId)
						.orElseThrow(() -> new CustomException(ErrorCode.PAYMENT_NOT_FOUND));

		if (!existPayment.getUserId().equals(userId)) {
			throw new CustomException(ErrorCode.PAYMENT_UNAUTHORIZED);
		}

		return PaymentResDTO.from(existPayment);
	}

	@Transactional(readOnly = true)
	public Page<PaymentResDTO> getPaymentList(Long userId, Pageable pageable) {
		Page<Payment> payments = paymentRepository.findByUserId(userId, pageable);
		return payments.map(PaymentResDTO::from);
	}

	@Transactional(readOnly = true)
	public Page<PaymentDetailResDTO> findPaymentListByStoreId(
			UUID storeId, Long userId, Pageable pageable) {

		Store store =
				storeRepository
						.findById(storeId)
						.orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));

		if (store.getUser().getUserId().equals(userId)) {
			throw new CustomException(ErrorCode.PAYMENT_FIND_UNAUTHORIZED);
		}

		Page<Payment> payments = paymentRepository.findByStoreId(storeId, pageable);
		return payments.map(PaymentDetailResDTO::from);
	}
}

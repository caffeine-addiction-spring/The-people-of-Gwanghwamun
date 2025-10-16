package com.caffeine.gwanghwamun.domain.payment.dto;

import com.caffeine.gwanghwamun.domain.payment.entity.Payment;
import com.caffeine.gwanghwamun.domain.payment.entity.PaymentStatus;
import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentResDTO(
		UUID paymentId,
		UUID orderId,
		Integer amount,
		PaymentStatus paymentStatus,
		LocalDateTime approvedAt) {
	public static PaymentResDTO from(Payment payment) {
		return new PaymentResDTO(
				payment.getPaymentId(),
				payment.getOrderId(),
				payment.getAmount(),
				payment.getPaymentStatus(),
				payment.getApprovedAt());
	}
}

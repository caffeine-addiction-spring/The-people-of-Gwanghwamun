package com.caffeine.gwanghwamun.domain.payment.dto;

import com.caffeine.gwanghwamun.domain.payment.entity.Payment;
import com.caffeine.gwanghwamun.domain.payment.entity.PaymentStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentDetailResDTO(
        UUID paymentId,
        UUID orderId,
        Long userId,
        Integer amount,
        PaymentStatus paymentStatus,
        LocalDateTime approvedAt,
        LocalDateTime canceledAt
) {
    public static PaymentDetailResDTO from(Payment payment) {
        return new PaymentDetailResDTO(
                payment.getPaymentId(),
                payment.getOrderId(),
                payment.getUserId(),
                payment.getAmount(),
                payment.getPaymentStatus(),
                payment.getApprovedAt(),
                payment.getCanceledAt()
        );
    }
}

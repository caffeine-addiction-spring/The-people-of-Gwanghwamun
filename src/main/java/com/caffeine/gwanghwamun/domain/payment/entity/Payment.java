package com.caffeine.gwanghwamun.domain.payment.entity;

import com.caffeine.gwanghwamun.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Payment extends BaseEntity {

    @Id
    private UUID paymentId;

    private UUID orderId;

    private Long payment_key;

    private Long amount;

    private PaymentStatus paymentStatus;

    private LocalDateTime approvedAt;

    private LocalDateTime canceledAt;
}

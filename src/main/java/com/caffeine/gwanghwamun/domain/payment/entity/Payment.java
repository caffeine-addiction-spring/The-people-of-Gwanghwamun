package com.caffeine.gwanghwamun.domain.payment.entity;

import com.caffeine.gwanghwamun.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "p_payment")
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "payment_id")
    private UUID paymentId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @Column(name = "store_id", nullable = false)
    private UUID storeId;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "payment_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "canceled_at")
    private LocalDateTime canceledAt;

    @Builder
    public Payment(
            Long userId, UUID orderId, UUID storeId,Integer amount, PaymentStatus paymentStatus, LocalDateTime approvedAt, LocalDateTime canceledAt
    ){
        this.userId = userId;
        this.orderId = orderId;
        this.storeId = storeId;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.approvedAt = approvedAt;
        this.canceledAt = canceledAt;
    }

    public void setApprovedAt(){
        this.paymentStatus = PaymentStatus.APPROVE;
        this.approvedAt = LocalDateTime.now();
    }

    public void setCanceledAt(){
        this.paymentStatus = PaymentStatus.CANCELED;
        this.canceledAt = LocalDateTime.now();
    }
}

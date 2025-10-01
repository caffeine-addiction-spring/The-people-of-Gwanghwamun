package com.caffeine.gwanghwamun.domain.order.entity;

import com.caffeine.gwanghwamun.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "p_order")
@NoArgsConstructor
public class Order extends BaseEntity {
    @Id
    private UUID order_id = UUID.randomUUID();

    private int totalPrice;

    private String requests;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private String deliveryAddress;

    private String deliveryContent;

    private LocalDateTime deletedDate;

    private String deletedBy;

    private UUID storeId;

    private UUID userId;



}

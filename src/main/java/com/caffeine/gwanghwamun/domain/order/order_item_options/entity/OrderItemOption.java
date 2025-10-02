package com.caffeine.gwanghwamun.domain.order.order_item_options.entity;

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
@Table(name = "p_order_item_option")
@NoArgsConstructor
public class OrderItemOption extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID order_id;

    private int totalPrice;

    private String requests;

    private String deliveryAddress;

    private String deliveryContent;

    private LocalDateTime deletedDate;

    private String deletedBy;

    private UUID storeId;

    private UUID userId;


}

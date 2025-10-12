package com.caffeine.gwanghwamun.domain.order.order_status_log.entity;

import com.caffeine.gwanghwamun.domain.BaseEntity;
import com.caffeine.gwanghwamun.domain.order.entity.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "p_order_status_log")
@NoArgsConstructor
public class OrderStatusLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID orderStatusLogId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;

    private String existingState;
    private String currentState;
    private String reason;
}

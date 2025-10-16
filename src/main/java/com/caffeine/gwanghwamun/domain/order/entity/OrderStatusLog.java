package com.caffeine.gwanghwamun.domain.order.entity;

import com.caffeine.gwanghwamun.domain.BaseEntity;
import com.caffeine.gwanghwamun.domain.store.entity.Store;
import com.caffeine.gwanghwamun.domain.user.entity.User;
import jakarta.persistence.*;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "store_id")
  private Store store;

  @Enumerated(EnumType.STRING)
  private OrderStatus existingState;

  @Enumerated(EnumType.STRING)
  private OrderStatus currentState;

  private String reason;

  @Builder
  public OrderStatusLog(
      Order order,
      User user,
      Store store,
      OrderStatus existingState,
      OrderStatus currentState,
      String reason) {
    this.order = order;
    this.user = user;
    this.store = store;
    this.existingState = existingState;
    this.currentState = currentState;
    this.reason = reason;
  }
}

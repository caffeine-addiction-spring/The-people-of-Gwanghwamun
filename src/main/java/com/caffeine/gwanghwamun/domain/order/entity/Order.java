package com.caffeine.gwanghwamun.domain.order.entity;

import com.caffeine.gwanghwamun.domain.BaseEntity;
import com.caffeine.gwanghwamun.domain.cart.entity.Cart;
import com.caffeine.gwanghwamun.domain.store.entity.Store;
import com.caffeine.gwanghwamun.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "p_orders")
@NoArgsConstructor
public class Order extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "order_id", nullable = false)
  private UUID orderId;

  @Column(name = "total_price", nullable = false)
  private Integer totalPrice;

  @Column(name = "requests", nullable = false)
  private String requests;

  @Column(name = "order_status", nullable = false)
  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;

  @Column(name = "delivery_address", nullable = false)
  private String deliveryAddress;

  @Column(name = "delivery_content", nullable = false)
  private String deliveryContent;

  @Column(name = "deleted_date", nullable = true)
  private LocalDateTime deletedDate;

  @Column(name = "deleted_by", nullable = true)
  private String deletedBy;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cart_id")
  private Cart cart;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "store_id")
  private Store store;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @Builder
  public Order(
      Store store,
      User user,
      OrderStatus orderStatus,
      int totalPrice,
      String deliveryAddress,
      String requests) {
    this.store = store;
    this.user = user;
    this.orderStatus = orderStatus;
    this.totalPrice = totalPrice;
    this.requests = requests;
    this.deliveryAddress = deliveryAddress;
  }

  public void cancel() {
    this.orderStatus = OrderStatus.CANCELLED;
  }

  public void accept() {
    this.orderStatus = OrderStatus.ACCEPTED;
  }

  public void reject() {
    this.orderStatus = OrderStatus.REJECTED;
  }

  public void completeCooking() {
    this.orderStatus = OrderStatus.COOKING_COMPLETED;
  }

  public void completeDelivery() {
    this.orderStatus = OrderStatus.DELIVERY_COMPLETED;
  }

  public void updateTotalPrice(Integer totalPrice) {
    this.totalPrice = totalPrice;
  }
}

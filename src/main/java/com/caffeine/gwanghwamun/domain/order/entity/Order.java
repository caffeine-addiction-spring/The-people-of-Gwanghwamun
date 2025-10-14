package com.caffeine.gwanghwamun.domain.order.entity;

import com.caffeine.gwanghwamun.domain.BaseEntity;
import com.caffeine.gwanghwamun.domain.cart.entity.Cart;
import com.caffeine.gwanghwamun.domain.order_items.entity.OrderItem;
import com.caffeine.gwanghwamun.domain.order_status_log.entity.OrderStatusLog;
import com.caffeine.gwanghwamun.domain.store.entity.Store;
import com.caffeine.gwanghwamun.domain.user.entity.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
	private int totalPrice;

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

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderItem> orderItems;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderStatusLog> statusLogs;

	@Builder
	public Order(
			Store store,
			List<OrderItem> orderItems,
			OrderStatus orderStatus,
			int totalPrice,
			String deliveryAddress,
			String requests) {
		this.store = store;
		this.orderItems = orderItems != null ? orderItems : new ArrayList<>();
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
}

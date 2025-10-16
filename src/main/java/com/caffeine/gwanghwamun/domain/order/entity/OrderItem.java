package com.caffeine.gwanghwamun.domain.order.entity;

import com.caffeine.gwanghwamun.domain.BaseEntity;
import com.caffeine.gwanghwamun.domain.menu.entity.Menu;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "p_order_items")
@NoArgsConstructor
public class OrderItem extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID orderItemId;

	@Column(name = "deleted_date", nullable = true)
	private LocalDateTime deletedDate;

	@Column(name = "deleted_by", nullable = true)
	private String deletedBy;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menu_id")
	private Menu menu;

	@Column(name = "menu_name", nullable = false)
	private String menuName;

	@Column(name = "quantity", nullable = false)
	private Integer quantity;

	@Column(name = "price", nullable = false)
	private Integer price;

	@Builder
	public OrderItem(Order order, Menu menu, String menuName, Integer quantity, Integer price) {
		this.order = order;
		this.menu = menu;
		this.menuName = menuName;
		this.quantity = quantity;
		this.price = price;
	}
}

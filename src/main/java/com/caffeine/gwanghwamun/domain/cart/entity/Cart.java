package com.caffeine.gwanghwamun.domain.cart.entity;

import com.caffeine.gwanghwamun.domain.BaseEntity;
import com.caffeine.gwanghwamun.domain.menu.entity.Menu;
import com.caffeine.gwanghwamun.domain.store.entity.Store;
import com.caffeine.gwanghwamun.domain.user.entity.User;
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
@Table(name = "p_carts")
@NoArgsConstructor
public class Cart extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID cartId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id")
	private Store store;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menu_id")
	private Menu menu;

	private LocalDateTime deletedDate;

	private String deletedBy;

	@Column(name = "quantity", nullable = false)
	private int quantity;

	@Column(name = "total_price", nullable = false)
	private int totalPrice;

	@Builder
	public Cart(User user, Store store, Menu menu, int quantity, Integer totalPrice) {
		this.user = user;
		this.store = store;
		this.menu = menu;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
	}

	public void updateQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void delete(User user) {
		this.deletedDate = LocalDateTime.now();
		this.deletedBy = user.getName();
	}

	public void updateTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}
}

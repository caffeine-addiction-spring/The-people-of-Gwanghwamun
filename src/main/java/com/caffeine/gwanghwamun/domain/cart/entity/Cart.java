package com.caffeine.gwanghwamun.domain.cart.entity;

import com.caffeine.gwanghwamun.domain.BaseEntity;
import com.caffeine.gwanghwamun.domain.menu.entity.Menu;
import com.caffeine.gwanghwamun.domain.menu.entity.MenuOption;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menu_option_id")
	private MenuOption menuOption;

	@Column(name = "quantity", nullable = false)
	private int quantity;

	@Column(name = "total_price", nullable = false)
	private int totalPrice;

	@Enumerated(EnumType.STRING)
	private CartMode cartMode;

	@Builder
	public Cart(
			User user, Store store, Menu menu, MenuOption menuOption, int quantity, CartMode cartMode) {
		this.user = user;
		this.store = store;
		this.menu = menu;
		this.menuOption = menuOption;
		this.quantity = quantity;
		this.totalPrice = calculateTotalPrice(menu, menuOption, quantity);
		this.cartMode = cartMode;
	}

	public void updateQuantity(int quantity) {
		this.quantity = quantity;
		this.totalPrice = calculateTotalPrice(menu, menuOption, quantity);
	}

	public void updateMenuOption(MenuOption option) {
		this.menuOption = option;
		this.totalPrice = calculateTotalPrice(menu, menuOption, quantity);
	}

	public void delete(User user) {
		this.deletedDate = LocalDateTime.now();
		this.deletedBy = user.getName();
	}

	private int calculateTotalPrice(Menu menu, MenuOption option, int quantity) {
		return menuOption == null
				? quantity * menu.getPrice()
				: quantity * (menu.getPrice() + menuOption.getPrice());
	}
}

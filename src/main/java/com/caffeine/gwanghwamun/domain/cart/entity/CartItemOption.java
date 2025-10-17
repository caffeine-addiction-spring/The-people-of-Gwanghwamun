package com.caffeine.gwanghwamun.domain.cart.entity;

import com.caffeine.gwanghwamun.domain.menu.entity.MenuOption;
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
@Table(name = "p_cart_item_option")
@NoArgsConstructor
public class CartItemOption {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "cart_item_option_id", nullable = false)
	private UUID orderItemOptionId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menu_option_id", nullable = false)
	private MenuOption menuOption;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cart_id", nullable = false)
	private Cart cart;

	@Column(name = "deleted_date", nullable = true)
	private LocalDateTime deletedDate;

	@Column(name = "deleted_by", nullable = true)
	private String deletedBy;

	@Builder
	public CartItemOption(MenuOption menuOption, Cart cart) {
		this.cart = cart;
		this.menuOption = menuOption;
	}
}

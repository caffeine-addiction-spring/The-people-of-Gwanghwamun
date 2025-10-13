package com.caffeine.gwanghwamun.domain.order.order_items.order_item_options.entity;

import com.caffeine.gwanghwamun.domain.BaseEntity;
import com.caffeine.gwanghwamun.domain.order.order_items.entity.OrderItem;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "p_order_item_option")
@NoArgsConstructor
public class OrderItemOption extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID orderItemOptionId;

	private String optionName;

	private int optionPrice;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_item_id")
	private OrderItem orderItem;

	//    @ManyToOne(fetch = FetchType.LAZY)
	//    @JoinColumn(name = "menu_option_id")
	//    private MenuOption menuOption;

}

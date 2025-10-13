package com.caffeine.gwanghwamun.domain.order_items.entity;

import com.caffeine.gwanghwamun.domain.BaseEntity;
import com.caffeine.gwanghwamun.domain.menu.entity.Menu;
import com.caffeine.gwanghwamun.domain.order.entity.Order;
import com.caffeine.gwanghwamun.domain.order_item_options.entity.OrderItemOption;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "p_order_items")
@NoArgsConstructor
public class OrderItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID orderItemId;

    private LocalDateTime deletedDate;

    private String deletedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private String menuName;

    private int quantity;

    private int price;

    @OneToMany(mappedBy = "orderItem")
    private List<OrderItemOption> options;
}

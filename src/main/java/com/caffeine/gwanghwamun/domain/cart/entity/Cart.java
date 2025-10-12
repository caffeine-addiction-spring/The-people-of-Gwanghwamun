package com.caffeine.gwanghwamun.domain.cart.entity;

import com.caffeine.gwanghwamun.domain.BaseEntity;
import com.caffeine.gwanghwamun.domain.order.entity.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "p_carts")
@NoArgsConstructor
public class Cart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID cartId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "store_id")
//    private Store store;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "menu_id")
//    private Menu menu;

    private LocalDateTime deletedDate;

    private String deletedBy;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "menu_option_id")
//    private MenuOption menuOption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private CartMode cartMode; // DIRECT / CART

}

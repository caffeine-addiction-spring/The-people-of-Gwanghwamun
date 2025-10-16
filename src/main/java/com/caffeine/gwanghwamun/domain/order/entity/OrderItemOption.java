package com.caffeine.gwanghwamun.domain.order.entity;

import com.caffeine.gwanghwamun.domain.BaseEntity;
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
@Table(name = "p_order_item_option")
@NoArgsConstructor
public class OrderItemOption extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "order_item_option_id", nullable = false)
  private UUID orderItemOptionId;

  @Column(name = "option_name", nullable = false)
  private String optionName;

  @Column(name = "option_price", nullable = false)
  private Integer optionPrice;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_item_id", nullable = false)
  private OrderItem orderItem;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "menu_option_id", nullable = false)
  private MenuOption menuOption;

  @Column(name = "deleted_date", nullable = true)
  private LocalDateTime deletedDate;

  @Column(name = "deleted_by", nullable = true)
  private String deletedBy;

  @Builder
  public OrderItemOption(
      OrderItem orderItem, MenuOption menuOption, String optionName, Integer optionPrice) {
    this.orderItem = orderItem;
    this.menuOption = menuOption;
    this.optionName = optionName;
    this.optionPrice = optionPrice;
  }
}

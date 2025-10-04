package com.caffeine.gwanghwamun.domain.menu.entity;

import com.caffeine.gwanghwamun.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_menu")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "menu_id")
	private UUID menuId;

	@Column(name = "store_id", nullable = false)
	private UUID storeId;

	@Column(name = "group_id", nullable = false)
	private Long groupId;

	@Enumerated(EnumType.STRING)
	@Column(name = "menu_category", nullable = false, length = 20)
	private MenuCategory menuCategory;

	@Column(name = "name", nullable = false, length = 255)
	private String name;

	@Column(name = "menu_content", columnDefinition = "TEXT")
	private String menuContent;

	@Column(name = "price", nullable = false)
	private Integer price;

	@Column(name = "is_sold_out", nullable = false)
	private Boolean isSoldOut = false;

	@Column(name = "is_hidden", nullable = false)
	private Boolean isHidden = false;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	//@ManyToOne(fetch = FetchType.LAZY)
	//@JoinColumn(name = "store_id", nullable = false)
	//private Store store;

	@Builder
	public Menu(
			UUID storeId,
			Long groupId,
			MenuCategory menuCategory,
			String name,
			String menuContent,
			Integer price,
			Boolean isSoldOut,
			Boolean isHidden) {
		this.storeId = storeId;
		this.groupId = groupId;
		this.menuCategory = menuCategory;
		this.name = name;
		this.menuContent = menuContent;
		this.price = price;
		this.isSoldOut = isSoldOut;
		this.isHidden = isHidden;
	}

	public void updateMenu(
			String name, String menuContent, Integer price, Boolean isSoldOut, Boolean isHidden) {
		if (name != null) this.name = name;
		if (menuContent != null) this.menuContent = menuContent;
		if (price != null) this.price = price;
		if (isSoldOut != null) this.isSoldOut = isSoldOut;
		if (isHidden != null) this.isHidden = isHidden;
	}

	public void markAsSoldOut() {
		this.isSoldOut = true;
	}

	public void markAsAvailable() {
		this.isSoldOut = false;
	}

	public void hideMenu() {
		this.isHidden = true;
	}

	public void showMenu() {
		this.isHidden = false;
	}

	public void softDelete() {
		this.deletedAt = LocalDateTime.now();
	}

	public boolean isDeleted() {
		return this.deletedAt != null;
	}
}

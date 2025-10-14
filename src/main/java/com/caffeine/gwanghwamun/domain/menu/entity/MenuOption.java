package com.caffeine.gwanghwamun.domain.menu.entity;

import com.caffeine.gwanghwamun.domain.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_menu_option")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuOption extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID menuOptionId;

	@Column(name = "menu_id", nullable = false)
	private UUID menuId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menu_id", insertable = false, updatable = false)
	private Menu menu;

	@Column(nullable = false, length = 100)
	private String optionName;

	private int price;

	@Lob private String content;

	private boolean isHidden;

	private boolean isSoldOut;

	private LocalDateTime deletedAt;

	@Column(length = 100)
	private String deletedBy;

	@Builder
	public MenuOption(
			UUID menuId,
			String optionName,
			Integer price,
			String content,
			Boolean isHidden,
			Boolean isSoldOut) {
		this.menuId = menuId;
		this.optionName = optionName;
		this.price = price;
		this.content = content;
		this.isHidden = isHidden != null ? isHidden : false;
		this.isSoldOut = isSoldOut != null ? isSoldOut : false;
	}

	public void update(
			String optionName, Integer price, String content, Boolean isHidden, Boolean isSoldOut) {
		if (optionName != null) {
			this.optionName = optionName;
		}
		if (price != null) {
			this.price = price;
		}
		if (content != null) {
			this.content = content;
		}
		if (isHidden != null) {
			this.isHidden = isHidden;
		}
		if (isSoldOut != null) {
			this.isSoldOut = isSoldOut;
		}
	}

	public void hideOption() {
		this.isHidden = true;
	}

	public void showOption() {
		this.isHidden = false;
	}

	public void markSoldOut() {
		this.isSoldOut = true;
	}

	public void markAvailable() {
		this.isSoldOut = false;
	}

	public void softDelete(String deletedBy) {
		this.deletedAt = LocalDateTime.now();
		this.deletedBy = deletedBy;
	}

	public boolean isDeleted() {
		return this.deletedAt != null;
	}
}

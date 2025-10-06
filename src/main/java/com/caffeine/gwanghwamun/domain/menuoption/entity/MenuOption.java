package com.caffeine.gwanghwamun.domain.menuoption.entity;

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
	@Column(name = "menu_option_id")
	private UUID menuOptionId;

	@Column(name = "menu_id", nullable = false)
	private UUID menuId;

	@Column(name = "option_name", nullable = false, length = 100)
	private String optionName;

	@Column(name = "price", nullable = false)
	private Integer price;

	@Column(name = "content", length = 2000)
	private String content;

	@Column(name = "is_hidden", nullable = false)
	private Boolean isHidden = false;

	@Column(name = "is_sold_out", nullable = false)
	private Boolean isSoldOut = false;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	@Column(name = "deleted_by", length = 100)
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

	public void update(String optionName, Integer price, String content, Boolean isHidden) {
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

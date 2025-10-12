package com.caffeine.gwanghwamun.domain.menu.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MenuCategory {
	MAIN("메인"),
	SIDE("사이드"),
	DRINK("음료"),
	OTHER("기타"),
	SET("세트");

	private final String displayName;

	MenuCategory(String displayName) {
		this.displayName = displayName;
	}

	@JsonValue
	public String getDisplayName() {
		return displayName;
	}

	@JsonCreator
	public static MenuCategory fromDisplayName(String displayName) {
		for (MenuCategory cateogry : values()) {
			if (cateogry.getDisplayName().equals(displayName)) {
				return cateogry;
			}
		}
		throw new IllegalArgumentException("지원하지 않는 메뉴 카테고리입니다." + displayName);
	}
}

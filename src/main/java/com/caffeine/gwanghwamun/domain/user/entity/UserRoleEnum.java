package com.caffeine.gwanghwamun.domain.user.entity;

import lombok.Getter;

@Getter
public enum UserRoleEnum {
	CUSTOMER(Authority.CUSTOMER), // 고객
	MANAGER(Authority.MANAGER), // 관리자 - 서비스 담당자
	MASTER(Authority.MASTER), // 관리자 - 최종 관리자
	OWNER(Authority.OWNER); // 가게 주인

	private final String authority;

	UserRoleEnum(String authority) {
		this.authority = authority;
	}

	public static class Authority {
		public static final String CUSTOMER = "ROLE_CUSTOMER";
		public static final String MANAGER = "ROLE_MANAGER";
		public static final String MASTER = "ROLE_MASTER";
		public static final String OWNER = "ROLE_OWNER";
	}
}

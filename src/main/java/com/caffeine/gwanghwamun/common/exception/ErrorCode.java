package com.caffeine.gwanghwamun.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/* 필요 시 에러코드 추가 후 사용 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	// 공통
	VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "요청 값이 유효하지 않습니다."),

	// 회원 (user)
	DUPLICATED(HttpStatus.CONFLICT, "회원가입 실패"),
	FORBIDDEN(HttpStatus.FORBIDDEN, "인가 실패"),
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증 실패"),
	LOGIN_FAIL(HttpStatus.UNAUTHORIZED, "로그인 실패"),
	LOGOUT_FAIL(HttpStatus.UNAUTHORIZED, "로그아웃 실패"),

	// 메뉴 (menu)
	MENU_NOT_FOUND(HttpStatus.NOT_FOUND, "메뉴를 찾을 수 없습니다."),
	MENU_STORE_MISMATCH(HttpStatus.FORBIDDEN, "해당 가게의 메뉴가 아닙니다.");

	private final HttpStatus httpStatus;
	private final String message;
}

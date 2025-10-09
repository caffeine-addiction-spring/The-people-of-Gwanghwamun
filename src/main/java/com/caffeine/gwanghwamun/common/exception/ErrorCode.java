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
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자 조회 실패"),
	INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호 변경 실패"),
	ALREADY_DELETED_USER(HttpStatus.CONFLICT, "회원 삭제 실패"),

	// 메뉴 (menu)
	MENU_NOT_FOUND(HttpStatus.NOT_FOUND, "메뉴를 찾을 수 없습니다."),
	MENU_STORE_MISMATCH(HttpStatus.FORBIDDEN, "해당 가게의 메뉴가 아닙니다."),

	// 가게 (store)
	STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 가게를 찾을 수 없습니다."),
	ALREADY_DELETED(HttpStatus.BAD_REQUEST, "이미 삭제된 가게입니다."),

	// 메뉴 옵션 (menuOption)
	MENU_OPTION_NOT_FOUND(HttpStatus.NOT_FOUND, "메뉴 옵션을 찾을 수 없습니다."),
	MENU_OPTION_MENU_MISMATCH(HttpStatus.BAD_REQUEST, "옵션이 해당 메뉴에 속하지 않습니다."),
	MENU_OPTION_VISIBILITY_UPDATE_SUCCESS(HttpStatus.OK, "옵션 숨김 상태가 변경되었습니다.");


	private final HttpStatus httpStatus;
	private final String message;
}

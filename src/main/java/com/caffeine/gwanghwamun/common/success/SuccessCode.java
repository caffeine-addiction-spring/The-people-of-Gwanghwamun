package com.caffeine.gwanghwamun.common.success;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/* 필요 시 성공코드 추가 후 사용 */
@Getter
@RequiredArgsConstructor
public enum SuccessCode {

	// 회원 (user)
	USER_SAVE_SUCCESS(HttpStatus.CREATED, "회원가입 성공"),
	USER_LOGIN_SUCCESS(HttpStatus.OK, "로그인 성공"),

	// 메뉴 옵션 (menuOption)
	MENU_OPTION_SAVE_SUCCESS(HttpStatus.CREATED, "메뉴 옵션 생성 성공"),
	MENU_OPTION_FIND_SUCCESS(HttpStatus.OK, "메뉴 옵션 조회 성공"),
	MENU_OPTION_LIST_SUCCESS(HttpStatus.OK, "메뉴 옵션 목록 조회 성공"),
	MENU_OPTION_UPDATE_SUCCESS(HttpStatus.OK, "메뉴 옵션 수정 성공"),
	MENU_OPTION_DELETE_SUCCESS(HttpStatus.NO_CONTENT, "메뉴 옵션 삭제 성공"),
	MENU_OPTION_SOLDOUT_UPDATE_SUCCESS(HttpStatus.OK, "메뉴 옵션 품절 상태 변경 성공");

	private final HttpStatus httpStatus;
	private final String message;
}

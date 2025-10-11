package com.caffeine.gwanghwamun.common.success;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/* 필요 시 성공코드 추가 후 사용 */
@Getter
@RequiredArgsConstructor
public enum SuccessCode {

	// 리뷰 (review)
	REVIEW_SAVE_SUCCESS(HttpStatus.CREATED, "리뷰 생성 성공"),
	REVIEW_FIND_SUCCESS(HttpStatus.OK, "리뷰 단건 조회 생공"),
	REVIEW_LIST_FIND_SUCCESS(HttpStatus.OK, "리뷰 리스트 조회 성공"),
	REVIEW_DELETE_SUCCESS(HttpStatus.NO_CONTENT, "리뷰 삭제 성공"),

	// 회원 (user)
	USER_SAVE_SUCCESS(HttpStatus.CREATED, "회원가입 성공"),
	USER_LOGIN_SUCCESS(HttpStatus.OK, "로그인 성공"),
	USER_READ_SUCCESS(HttpStatus.OK, "회원 정보 조회 성공"),
	USER_UPDATE_SUCCESS(HttpStatus.OK, "회원 정보 수정 성공"),
	PASSWORD_UPDATE_SUCCESS(HttpStatus.OK, "비밀번호 수정 성공"),
	USER_DELETE_SUCCESS(HttpStatus.NO_CONTENT, "회원 삭제 성공"),

	// 메뉴 (menu)
	MENU_SAVE_SUCCESS(HttpStatus.CREATED, "메뉴 등록 성공"),
	MENU_FIND_SUCCESS(HttpStatus.OK, "메뉴 조회 성공"),
	MENU_LIST_SUCCESS(HttpStatus.OK, "메뉴 목록 조회 성공"),
	MENU_UPDATE_SUCCESS(HttpStatus.OK, "메뉴 수정 성공"),
	MENU_DELETE_SUCCESS(HttpStatus.NO_CONTENT, "메뉴 삭제 성공"),
	MENU_VISIBILITY_UPDATE_SUCCESS(HttpStatus.OK, "메뉴 숨김 상태 변경 성공"),
	MENU_SOLDOUT_UPDATE_SUCCESS(HttpStatus.OK, "메뉴 품절 상태 변경 성공"),

	// 가게 (store)
	STORE_DELETE_SUCCESS(HttpStatus.OK, "가게 삭제 완료");

	private final HttpStatus httpStatus;
	private final String message;
}

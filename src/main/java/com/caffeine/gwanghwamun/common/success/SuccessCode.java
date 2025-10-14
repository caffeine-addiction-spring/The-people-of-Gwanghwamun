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
	USER_READ_SUCCESS(HttpStatus.OK, "회원 정보 조회 성공"),
	USER_UPDATE_SUCCESS(HttpStatus.OK, "회원 정보 수정 성공"),
	PASSWORD_UPDATE_SUCCESS(HttpStatus.OK, "비밀번호 수정 성공"),
	USER_DELETE_SUCCESS(HttpStatus.NO_CONTENT, "회원 삭제 성공"),

	// 주소 (address)
	ADDRESS_SAVE_SUCCESS(HttpStatus.CREATED, "회원 주소 생성 성공"),
	ADDRESS_UPDATE_SUCCESS(HttpStatus.OK, "회원 주소 수정 성공"),
	ADDRESS_DELETE_SUCCESS(HttpStatus.NO_CONTENT, "회원 주소 삭제 성공"),
	ADDRESS_LIST_FETCH_SUCCESS(HttpStatus.OK, "회원 주소 목록 조회 성공"),
	ADDRESS_FETCH_SUCCESS(HttpStatus.OK, "회원 주소 상세 조회 성공"),
	DEFAULT_ADDRESS_SAVE_SUCCESS(HttpStatus.OK, "기본 배송지 주소 설정 성공"),

	// 메뉴 (menu)
	MENU_SAVE_SUCCESS(HttpStatus.CREATED, "메뉴 등록 성공"),
	MENU_FIND_SUCCESS(HttpStatus.OK, "메뉴 조회 성공"),
	MENU_LIST_SUCCESS(HttpStatus.OK, "메뉴 목록 조회 성공"),
	MENU_UPDATE_SUCCESS(HttpStatus.OK, "메뉴 수정 성공"),
	MENU_DELETE_SUCCESS(HttpStatus.NO_CONTENT, "메뉴 삭제 성공"),
	MENU_VISIBILITY_UPDATE_SUCCESS(HttpStatus.OK, "메뉴 숨김 상태 변경 성공"),
	MENU_SOLDOUT_UPDATE_SUCCESS(HttpStatus.OK, "메뉴 품절 상태 변경 성공"),

	// 가게 (store)
	STORE_DELETE_SUCCESS(HttpStatus.OK, "가게 삭제 완료"),

	// 메뉴 옵션 (menuOption)
	MENU_OPTION_SAVE_SUCCESS(HttpStatus.CREATED, "메뉴 옵션 생성 성공"),
	MENU_OPTION_FIND_SUCCESS(HttpStatus.OK, "메뉴 옵션 조회 성공"),
	MENU_OPTION_LIST_SUCCESS(HttpStatus.OK, "메뉴 옵션 목록 조회 성공"),
	MENU_OPTION_UPDATE_SUCCESS(HttpStatus.OK, "메뉴 옵션 수정 성공"),
	MENU_OPTION_DELETE_SUCCESS(HttpStatus.NO_CONTENT, "메뉴 옵션 삭제 성공"),
	MENU_OPTION_VISIBILITY_UPDATE_SUCCESS(HttpStatus.OK, "메뉴 숨김 상태 변경 성공"),
	MENU_OPTION_SOLDOUT_UPDATE_SUCCESS(HttpStatus.OK, "메뉴 옵션 품절 상태 변경 성공"),

	// 파일 (file)
	FILE_UPLOAD_SUCCESS(HttpStatus.CREATED, "파일 업로드 성공"),
	FILE_READ_SUCCESS(HttpStatus.OK, "파일 조회 성공"),
	FILE_DELETE_SUCCESS(HttpStatus.NO_CONTENT, "파일 삭제 성공"),

	// 장바구니 (cart)
	CART_SAVE_SUCCESS(HttpStatus.OK, "장바구니 추가 성공"),
	CART_LIST_SUCCESS(HttpStatus.OK, "장바구니 목록 조회 성공"),
	CART_UPDATE_SUCCESS(HttpStatus.OK, "장바구니 항목 수정 성공"),
	CART_DELETE_SUCCESS(HttpStatus.OK, "장바구니 삭제 성공"),

	// 주문 (order)
	ORDER_SAVE_SUCCESS(HttpStatus.OK, "주문 성공"),
	ORDER_LIST_SUCCESS(HttpStatus.OK, "주문 목록 조회 성공"),
	ORDER_FIND_SUCCESS(HttpStatus.OK, "주문 상세조회 성공"),
	ORDER_CANCEL_SUCCESS(HttpStatus.OK, "주문 취소 성공"),
	ORDER_ACCEPT_SUCCESS(HttpStatus.OK, "주문 수락 성공"),
	ORDER_REJECT_SUCCESS(HttpStatus.OK, "주문 거절 성공"),
	ORDER_COOK_COMPLETE_SUCCESS(HttpStatus.OK, "조리 완료"),
	ORDER_DELIVERY_COMPLETE_SUCCESS(HttpStatus.OK, "배달 완료"),

	// 주문 상태 로그 (order_status_log)
	ORDER_LOG_LIST_SUCCESS(HttpStatus.OK, "주문 상태 목록 조회 성공"),
	ORDER_LOG_SUCCESS(HttpStatus.OK, "주문 상태 조회 성공");

	private final HttpStatus httpStatus;
	private final String message;
}

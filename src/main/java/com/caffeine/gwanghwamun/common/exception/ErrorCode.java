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
	SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."),

	// 결제 (payment)
	PAYMENT_INVALID_AMOUNT(HttpStatus.BAD_REQUEST, "결제 금액이 유효하지 않습니다."),
	PAYMENT_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "사용자 정보가 일치하지 않습니다."),
	PAYMENT_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "이미 결제된 주문입니다."),
	PAYMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "결제 정보를 찾을 수 없습니다."),
	PAYMENT_FIND_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "결제 목록 조회 권한이 없습니다."),

	// 리뷰 (review)
	REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "리뷰를 찾을 수 없습니다."),
	REVIEW_DELETE_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "리뷰 삭제 권한이 없습니다."),
	REVIEW_CREATE_NOT_ACCEPTED(HttpStatus.BAD_REQUEST, "배달 완료 상태가 아닙니다."),
	REVIEW_DUPLICATED(HttpStatus.BAD_REQUEST, "주문당 1개의 리뷰만 작성할 수 있습니다."),
	REVIEW_CREATE_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "댓글 생성은 본인만 생성할 수 있습니다."),
	REVIEW_REPLY_CREATE_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "답글 생성 권한이 없습니다."),
	REVIEW_REPLY_DELETE_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "답글 삭제 권한이 없습니다."),
	REVIEW_REPLY_DUPLICATED(HttpStatus.CONFLICT, "이미 답글이 등록되어 있습니다."),

	// 회원 (user)
	DUPLICATED(HttpStatus.CONFLICT, "회원가입 실패"),
	FORBIDDEN(HttpStatus.FORBIDDEN, "인가 실패"),
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증 실패"),
	LOGIN_FAIL(HttpStatus.UNAUTHORIZED, "로그인 실패"),
	LOGOUT_FAIL(HttpStatus.UNAUTHORIZED, "로그아웃 실패"),
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자 조회 실패"),
	INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호 변경 실패"),
	ALREADY_DELETED_USER(HttpStatus.CONFLICT, "회원 삭제 실패"),

	// 주소 (address)
	ADDRESS_NOT_FOUND(HttpStatus.NOT_FOUND, "회원 주소 조회 실패"),
	ALREADY_DELETED_ADDRESS(HttpStatus.CONFLICT, "회원 주소 삭제 실패"),

	// 메뉴 (menu)
	MENU_NOT_FOUND(HttpStatus.NOT_FOUND, "메뉴를 찾을 수 없습니다."),
	MENU_STORE_MISMATCH(HttpStatus.FORBIDDEN, "해당 가게의 메뉴가 아닙니다."),
	ORDER_UNABLE_MENU(HttpStatus.FORBIDDEN, "주문 불가능한 메뉴입니다."),
	ORDER_UNABLE_MENU_OPTION(HttpStatus.FORBIDDEN, "주문 불가능한 메뉴옵션입니다."),

	// 가게 (store)
	STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 가게를 찾을 수 없습니다."),
	ALREADY_DELETED(HttpStatus.BAD_REQUEST, "이미 삭제된 가게입니다."),
	DUPLICATED_STORE(HttpStatus.CONFLICT, "이미 등록된 가게입니다."),
	OWNER_REQUIRED(HttpStatus.BAD_REQUEST, "가게 등록 시 OWNER 사용자를 지정해야 합니다."),
	INVALID_ROLE(HttpStatus.BAD_REQUEST, "지정된 사용자가 OWNER 권한이 아닙니다."),
	INVALID_REQUEST(HttpStatus.BAD_REQUEST, "OWNER는 userId를 지정할 수 없습니다."),

	// 지역 (region)
	REGION_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 지역을 찾을 수 없습니다."),
	REGION_DUPLICATED(HttpStatus.CONFLICT, "이미 존재하는 지역명입니다."),

	// 메뉴 옵션 (menuOption)
	MENU_OPTION_NOT_FOUND(HttpStatus.NOT_FOUND, "메뉴 옵션을 찾을 수 없습니다."),
	MENU_OPTION_MENU_MISMATCH(HttpStatus.BAD_REQUEST, "옵션이 해당 메뉴에 속하지 않습니다."),
	MENU_OPTION_VISIBILITY_UPDATE_SUCCESS(HttpStatus.OK, "옵션 숨김 상태가 변경되었습니다."),

	// 파일 (file)
	FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 파일을 찾을 수 없습니다."),
	FILE_NOT_UPLOAD(HttpStatus.BAD_REQUEST, "파일을 업로드할 수 없습니다."),

	// 주문 (order)
	ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 주문을 찾을 수 없습니다."),
	ORDER_TIME_EXPIRED(HttpStatus.BAD_REQUEST, "취소 가능 시간이 초과되었습니다."),
	LOW_MIN_DELEVERY_PRICE(HttpStatus.BAD_REQUEST, "최소 주문금액 미만입니다.."),
	UNAUTHORIZED_ORDER_ACCESS(HttpStatus.FORBIDDEN, "주문에 접근권한이 없습니디."),
	UNAUTHORIZED_STORE_ACCESS(HttpStatus.FORBIDDEN, "가게에 대한 권한이 없습니다."),

	// 장바구니 (cart)
	CART_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 장바구니를 찾을 수 없습니다."),
	UNAUTHORIZED_CART_ACCESS(HttpStatus.FORBIDDEN, "장바구니에 접근권한이 없습니디.");

	private final HttpStatus httpStatus;
	private final String message;
}

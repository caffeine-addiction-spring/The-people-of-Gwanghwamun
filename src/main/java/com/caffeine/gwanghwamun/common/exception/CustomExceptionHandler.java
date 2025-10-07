package com.caffeine.gwanghwamun.common.exception;

import com.caffeine.gwanghwamun.common.response.ApiResponse;
import com.caffeine.gwanghwamun.common.response.ResponseUtil;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(CustomException.class)
	protected ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException e) {
		ErrorCode errorCode = e.getErrorCode();
		return ResponseUtil.failureResponse(
				errorCode.getMessage(), errorCode.name(), errorCode.getHttpStatus());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ApiResponse<Void>> handleValidationException(
			MethodArgumentNotValidException e) {

		List<String> errorMessages =
				e.getBindingResult().getFieldErrors().stream()
						.map(
								fieldError ->
										String.format("[%s] %s", fieldError.getField(), fieldError.getDefaultMessage()))
						.collect(Collectors.toList());

		String combinedMessage = String.join(", ", errorMessages);

		return ResponseUtil.failureResponse(
				combinedMessage,
				ErrorCode.VALIDATION_ERROR.name(),
				ErrorCode.VALIDATION_ERROR.getHttpStatus());
	}

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseUtil.failureResponse(
                e.getMessage(),
                ErrorCode.STORE_NOT_FOUND.name(),
                ErrorCode.STORE_NOT_FOUND.getHttpStatus());
    }

}

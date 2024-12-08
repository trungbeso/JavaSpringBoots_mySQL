package com.trungbeso.exception;

import com.trungbeso.dto.request.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

//khai bao
@ControllerAdvice
public class GlobalExceptionHandler {


	@ExceptionHandler(value = AppException.class)
	ResponseEntity<ApiResponse> handlingAppException(AppException exception) {
		ErrorCode errorCode = exception.getErrorCode();
		ApiResponse apiResponse = new ApiResponse();

		apiResponse.setCode(errorCode.getCode());
		apiResponse.setMessage(errorCode.getMessage());

		return ResponseEntity.badRequest().body(apiResponse);
	}

	@ExceptionHandler(value = Exception.class)
	ResponseEntity<ApiResponse> handlingRuntimeException(Exception exception) {
		ApiResponse apiResponse = new ApiResponse();

		apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
		apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

		return ResponseEntity.badRequest().body(apiResponse);
	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException exception) {
		String enumKey = null;
		if (exception.getFieldError() != null) {
			enumKey = exception.getFieldError().getDefaultMessage();
		}

		ErrorCode errorCode = ErrorCode.INVALID_KEY; // Default error code
		if (enumKey != null) {
			try {
				errorCode = ErrorCode.valueOf(enumKey);
			} catch (IllegalArgumentException e) {
				// Log the exception if necessary
			}
		}

		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setCode(errorCode.getCode());
		apiResponse.setMessage(errorCode.getMessage());

		return ResponseEntity.badRequest().body(apiResponse);
	}
}

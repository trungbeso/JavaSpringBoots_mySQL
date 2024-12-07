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

	// Gom tất cả các runtime exception vào đây để xử lý
	@ExceptionHandler(value = RuntimeException.class)
	ResponseEntity<ApiResponse> handlingRuntimeException(Exception exception) { // Spring tiêm exception vào param
		ApiResponse apiResponse = new ApiResponse();

		apiResponse.setCode(404);
		apiResponse.setMessage(exception.getMessage());

		return ResponseEntity.badRequest().body(apiResponse);
	}

	@ExceptionHandler(value = AppException.class)
	ResponseEntity<ApiResponse> handlingAppException(AppException exception) { // Spring tiêm exception vào param
		ErrorCode errorCode = exception.getErrorCode();
		ApiResponse apiResponse = new ApiResponse();

		apiResponse.setCode(errorCode.getCode());
		apiResponse.setMessage(errorCode.getMessage());

		return ResponseEntity.badRequest().body(apiResponse);
	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	ResponseEntity<String> handlingMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
		return ResponseEntity.badRequest().body(Objects.requireNonNull(exception.getFieldError()).getDefaultMessage());
	}

	//Fallback
	// Xử lý những Exception không expect
	@ExceptionHandler(value = Exception.class)
	ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException exception) { // Spring tiêm exception vào param
		ApiResponse apiResponse = new ApiResponse();

		apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
		apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

		return ResponseEntity.badRequest().body(apiResponse);
	}
}

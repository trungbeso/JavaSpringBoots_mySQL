package com.trungbeso.exception;

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
	ResponseEntity<String> handlingRuntimeException(RuntimeException exception) { // Spring tiêm exception vào param
		return ResponseEntity.badRequest().body(exception.getMessage());
	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	ResponseEntity<String> handlingMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
		return ResponseEntity.badRequest().body(Objects.requireNonNull(exception.getFieldError()).getDefaultMessage());
	}
}

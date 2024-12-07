package com.trungbeso.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	INVALID_KEY(202, "Invalid message key"),
	INVALID_PASSWORD(303, "Password must have at least eight characters"),
	USERNAME_INVALID(444, "User name must have at least three characters"),
	UNCATEGORIZED_EXCEPTION(666, "Uncategorized Exception"),
	USER_EXISTED(1001, "User already existed"),
	;

	private int code;
	private String message;


}

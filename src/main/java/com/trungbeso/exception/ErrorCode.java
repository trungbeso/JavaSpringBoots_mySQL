package com.trungbeso.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {


	UNCATEGORIZED_EXCEPTION(666, "Uncategorized Exception"),
	USER_EXISTED(1001, "User already existed"),
	;

	private int code;
	private String message;


}

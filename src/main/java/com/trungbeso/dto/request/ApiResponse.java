package com.trungbeso.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// Khai báo cho session biết khi nào serialize Object sang Json, field nào null -> không hiển thị
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse <T> {
	// Tạm quy định api nào trả về code 555 là thành công
	private int code = 555;
	private String message;
	private T result;
}

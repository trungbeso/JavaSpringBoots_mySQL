package com.trungbeso.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
// Khai báo cho session biết khi nào serialize Object sang Json, field nào null -> không hiển thị
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
	// Tạm quy định api nào trả về code 555 là thành công
	int code = 555;
	String message;
	T result;
}

package com.trungbeso.dto.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {
	private String password;
	private String firstName;
	private String lastName;
	private LocalDate dob;
}

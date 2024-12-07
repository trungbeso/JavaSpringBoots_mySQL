package com.trungbeso.dto.request;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserCreationRequest {

	@Size(min = 3, message = "USERNAME_INVALID")
	private String username;

	@Size(min = 8, message = "INVALID_PASSWORD")
	private String password;

	private String firstName;

	private String lastName;

	@Past
	private LocalDate dob;


}

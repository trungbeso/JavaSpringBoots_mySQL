package com.trungbeso.controller;

import com.nimbusds.jose.JOSEException;
import com.trungbeso.dto.request.ApiResponse;
import com.trungbeso.dto.request.AuthenticationRequest;
import com.trungbeso.dto.request.IntrospectRequest;
import com.trungbeso.dto.response.AuthenticationResponse;
import com.trungbeso.dto.response.IntrospectResponse;
import com.trungbeso.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

	AuthenticationService authenticationService;

	@PostMapping("/log-in")
	ApiResponse<AuthenticationResponse> authenticated(@RequestBody AuthenticationRequest authenticationRequest) {
		// DI AuthenticationService Bean
		var result = authenticationService.authenticate(authenticationRequest);
		return ApiResponse.<AuthenticationResponse>builder()
			  .result(result)
			  .build();
	}

	@PostMapping("/introspect")
	ApiResponse<IntrospectResponse> authenticated(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
		// DI AuthenticationService Bean
		var result = authenticationService.introspectResponse(request);
		return ApiResponse.<IntrospectResponse>builder()
			  .result(result)
			  .build();
	}
}

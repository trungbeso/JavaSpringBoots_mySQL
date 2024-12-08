package com.trungbeso.service;

import com.trungbeso.dto.request.AuthenticationRequest;

import com.trungbeso.exception.AppException;
import com.trungbeso.exception.ErrorCode;
import com.trungbeso.repositories.IUserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

	IUserRepository userRepository;

	public boolean authenticate(AuthenticationRequest authenticationRequest) {
		var user = userRepository.findByUsername(authenticationRequest.getUsername())
			  .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
		return passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());
	}
}

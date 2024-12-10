package com.trungbeso.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.trungbeso.dto.request.AuthenticationRequest;

import com.trungbeso.dto.response.AuthenticationResponse;
import com.trungbeso.exception.AppException;
import com.trungbeso.exception.ErrorCode;
import com.trungbeso.repositories.IUserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

	IUserRepository userRepository;

	// danh dau de khong a-sign vao constructor
	@NonFinal
	protected static final String SIGNER_KEY = "FOBlK1xxWCZYEQx71eAko7zOny5EXHS2GG4RaaEIYkcpif6R5jhhJXq7BWsY5EEZ\n";

	public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
		var user = userRepository.findByUsername(authenticationRequest.getUsername())
			  .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
		boolean authenticated =  passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());

		if (!authenticated) {
			throw  new AppException(ErrorCode.UNAUTHENTICATED);
		}

		var token = generateToken(authenticationRequest.getUsername());

		return AuthenticationResponse.builder()
			  .token(token)
			  .isAuthenticated(true)
			  .build();

	}

	private String generateToken(String userName) {
		//Header
		JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

		// Claim
		JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
			  .subject(userName) // Dai dien cho user dang nhap
			  .issuer("Trungbeso") // Xac dinh token duoc issue tu ai (domain service)
			  .issueTime(new Date()) //
			  .expirationTime(new Date(
				    Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
			  )) // xac dinh han su dung
			  .claim("authorities", "ROLE_USER")
			  .build();

		// Payload
		Payload payload = new Payload(jwtClaimsSet.toJSONObject());

		JWSObject jwsObject = new JWSObject(header, payload);

		//Sign this token
		try {
			jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
			return jwsObject.serialize();
		} catch (JOSEException e) {
			log.error("Cannot create token", e);
			throw new RuntimeException(e);
		}
	}
}

package com.trungbeso.service;

import com.trungbeso.dto.request.UserCreationRequest;
import com.trungbeso.dto.request.UserUpdateRequest;
import com.trungbeso.dto.response.UserResponse;
import com.trungbeso.entity.User;
import com.trungbeso.exception.AppException;
import com.trungbeso.exception.ErrorCode;
import com.trungbeso.mapper.UserMapper;
import com.trungbeso.repositories.IUserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
	// Autowired dùng để Inject Bean vào class
	//	@Autowired
	IUserRepository userRepository;

	// @Autowired
	UserMapper userMapper;

	public User createUser(UserCreationRequest request) {
		// Kiểm tra User có tồn tại hay ko
		if (userRepository.existsByUsername(request.getUsername())) {
			throw new AppException(ErrorCode.USER_EXISTED);
		}

		// Mapping request vào user
//		User user = userMapper.toUser(request);

		User user = userMapper.toUser(request);
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
		user.setPassword(passwordEncoder.encode(request.getPassword()));

		// Create a new row in the table (persist into database)
		return userRepository.save(user);
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public UserResponse getUserById(String id) {
		return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
	}

	//Update
	public UserResponse updateUser(String userId, UserUpdateRequest request) {
//		User user = getUserById(userId);
//
//		user.setPassword(request.getPassword());
//		user.setFirstName(request.getFirstName());
//		user.setLastName(request.getLastName());
//		user.setDob(request.getDob());

		User user = userRepository.findById(userId)
			  .orElseThrow(() -> new RuntimeException("User not found"));

		userMapper.updateUser(user, request);

		return userMapper.toUserResponse(userRepository.save(user));
	}

	//Delete
	public void deleteUser(String userId) {
		userRepository.deleteById(userId);
	}

	public List<User> getUsers() {
		return userRepository.findAll();
	}

	public UserResponse getUser(String userId) {
		return userMapper.toUserResponse(userRepository.findById(userId)
			  .orElseThrow(() -> new RuntimeException("User not found")));
	}
}

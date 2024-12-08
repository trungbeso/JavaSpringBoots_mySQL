package com.trungbeso.service;

import com.trungbeso.dto.request.UserCreationRequest;
import com.trungbeso.dto.request.UserUpdateRequest;
import com.trungbeso.entity.User;
import com.trungbeso.exception.AppException;
import com.trungbeso.exception.ErrorCode;
import com.trungbeso.mapper.UserMapper;
import com.trungbeso.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {
	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private UserMapper userMapper;

	public User createUser(UserCreationRequest request) {
		// Kiểm tra User có tồn tại hay ko
		if (userRepository.existsByUsername(request.getUsername())) {
			throw new AppException(ErrorCode.USER_EXISTED);
		}

		// Mapping request vào user
		User user = userMapper.toUser(request);

		// Create a new row in the table (persist into database)
		return userRepository.save(user);
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User getUserById(String id) {
		return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
	}

	//Update
	public User updateUser(String userId, UserUpdateRequest request) {
//		User user = getUserById(userId);
//
//		user.setPassword(request.getPassword());
//		user.setFirstName(request.getFirstName());
//		user.setLastName(request.getLastName());
//		user.setDob(request.getDob());

		User user = getUserById(userId);
		userMapper.updateUser(user, request);

		return userRepository.save(user);
	}

	//Delete
	public void deleteUser(String userId) {
		userRepository.deleteById(userId);
	}
}

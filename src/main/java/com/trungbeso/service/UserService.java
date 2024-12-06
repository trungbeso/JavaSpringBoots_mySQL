package com.trungbeso.service;

import com.trungbeso.dto.request.UserCreationRequest;
import com.trungbeso.dto.request.UserUpdateRequest;
import com.trungbeso.entity.User;
import com.trungbeso.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {
	@Autowired
	private IUserRepository userRepository;

	public User createUser(UserCreationRequest request) {
		User user = new User();

		if (userRepository.existsByUsername(request.getUsername())) {
			throw new RuntimeException("Username already exists");
		}

		user.setUsername(request.getUsername());
		user.setPassword(request.getPassword());
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setDob(request.getDob());

		// Create a new row in the table
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
		User user = getUserById(userId);

		user.setPassword(request.getPassword());
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setDob(request.getDob());

		return userRepository.save(user);
	}

	//Delete
	public void deleteUser(String userId) {
		userRepository.deleteById(userId);
	}
}

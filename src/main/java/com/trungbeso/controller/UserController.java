package com.trungbeso.controller;

import com.trungbeso.dto.request.UserCreationRequest;
import com.trungbeso.dto.request.UserUpdateRequest;
import com.trungbeso.entity.User;
import com.trungbeso.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// Controller tuong tac truc tiep voi cac class trong lop service
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserService userService;

	//create Endpoint
	@PostMapping
	User createUser(@RequestBody @Valid UserCreationRequest request) {
		return userService.createUser(request);
	}

	// Lấy danh sách user
	@GetMapping
	List<User> getUsers() {
		return userService.getAllUsers();
	}

	// Lấy ra 1 user
	@GetMapping("/{userId}")
	User getUser(@PathVariable("userId") String userId) {
		return userService.getUserById(userId);
	}

	// Update thong tin user
	@PutMapping("/{userId}")
	User updateUser(@PathVariable String userId,@RequestBody UserUpdateRequest userUpdateRequest) {
		return userService.updateUser(userId, userUpdateRequest);
	}

	@DeleteMapping("/{userId}")
	String deleteUser(@PathVariable String userId) {
		userService.deleteUser(userId);
		return "User deleted";
	}
}

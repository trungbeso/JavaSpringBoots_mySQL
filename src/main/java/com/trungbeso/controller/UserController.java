package com.trungbeso.controller;

import com.trungbeso.dto.request.ApiResponse;
import com.trungbeso.dto.request.UserCreationRequest;
import com.trungbeso.dto.request.UserUpdateRequest;
import com.trungbeso.dto.response.UserResponse;
import com.trungbeso.entity.User;
import com.trungbeso.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
// Controller tuong tac truc tiep voi cac class trong lop service
@RequestMapping("/users")
public class UserController {
	//	@Autowired
	UserService userService;

	//create Endpoint
	@PostMapping
	ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request) {
		ApiResponse<User> apiResponse = new ApiResponse<>();
		apiResponse.setResult(userService.createUser(request));
		return apiResponse;
	}

	// Lấy danh sách user
	@GetMapping
	List<User> getUsers() {
		return userService.getAllUsers();
	}

	// Lấy ra 1 user
	@GetMapping("/{userId}")
	UserResponse getUser(@PathVariable("userId") String userId) {
		return userService.getUserById(userId);
	}

	// Update thong tin user
	@PutMapping("/{userId}")
	UserResponse updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest userUpdateRequest) {
		return userService.updateUser(userId, userUpdateRequest);
	}

	@DeleteMapping("/{userId}")
	String deleteUser(@PathVariable String userId) {
		userService.deleteUser(userId);
		return "User deleted";
	}
}

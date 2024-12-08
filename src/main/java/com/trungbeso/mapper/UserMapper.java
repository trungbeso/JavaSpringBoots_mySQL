package com.trungbeso.mapper;

import com.trungbeso.dto.request.UserCreationRequest;
import com.trungbeso.dto.request.UserUpdateRequest;
import com.trungbeso.dto.response.UserResponse;
import com.trungbeso.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

// Khai báo cho mapstruct biết class Mapper này sử dụng trong Spring để DI
@Mapper(componentModel = "spring")
public interface UserMapper {


		UserResponse toUserResponse(User user);

	//Mapping 2 Object k cung attribute
//	@Mapping(source = "firstName", target = "lastName")
//	UserResponse toUserResponse(User user);

	User toUser(UserCreationRequest userCreationRequest);

	void updateUser(@MappingTarget User user, UserUpdateRequest userUpdateRequest);
}

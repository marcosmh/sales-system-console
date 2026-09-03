package com.mark.microsystem.sales.system.main.service;

import com.mark.microsystem.sales.system.main.model.dto.UserCreateRequest;
import com.mark.microsystem.sales.system.main.model.dto.UserResponse;
import com.mark.microsystem.sales.system.main.model.dto.UserUpdateRequest;
import com.mark.microsystem.sales.system.main.model.entity.UserPerson;

import java.util.List;

public interface IUserService {

    UserResponse createUser(UserCreateRequest userRequest);

    UserResponse updateUser(Integer id, UserUpdateRequest userRequest);

    void deleteUser(Integer id);

    List<UserResponse> listUsers();

    UserResponse getUserById(Integer id);

    UserResponse getUserByName(String username);


}

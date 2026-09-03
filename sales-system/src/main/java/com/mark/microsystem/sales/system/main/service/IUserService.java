package com.mark.microsystem.sales.system.main.service;

import com.mark.microsystem.sales.system.main.model.entity.UserPerson;

import java.util.List;

public interface IUserService {

    UserPerson createUser(String username, String password, String role);

    UserPerson updateUser(Integer id, String newUsername, String newRole);

    void deleteUser(Integer id);

    List<UserPerson> listUsers();


}

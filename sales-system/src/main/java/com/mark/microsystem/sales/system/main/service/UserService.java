package com.mark.microsystem.sales.system.main.service;

import com.mark.microsystem.sales.system.main.model.entity.UserPerson;
import com.mark.microsystem.sales.system.main.repository.UserPersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserPersonRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserPerson createUser(String username, String password, String role) {
        UserPerson user = UserPerson.builder()
                .username(username)
                .passwordHash(passwordEncoder.encode(password))
                .role(role)
                .active(true)
                .build();
        return userRepository.save(user);
    }

    @Override
    public UserPerson updateUser(Integer id, String newUsername, String newRole) {
        UserPerson user = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User not found."));
        user.setUsername(newUsername);
        user.setRole(newRole);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserPerson> listUsers() {
        return userRepository.findAll();
    }
}

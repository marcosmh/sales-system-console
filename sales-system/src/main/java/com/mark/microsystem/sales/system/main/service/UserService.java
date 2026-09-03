package com.mark.microsystem.sales.system.main.service;

import com.mark.microsystem.sales.system.main.exception.ResourceNotFoundException;
import com.mark.microsystem.sales.system.main.model.dto.UserCreateRequest;
import com.mark.microsystem.sales.system.main.model.dto.UserResponse;
import com.mark.microsystem.sales.system.main.model.dto.UserUpdateRequest;
import com.mark.microsystem.sales.system.main.model.entity.UserPerson;
import com.mark.microsystem.sales.system.main.repository.UserPersonRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;



import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements IUserService {

    private final UserPersonRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(UserCreateRequest userRequest) {

        if(userRepository.existsByUsername(userRequest.username())) {
            throw new ResourceNotFoundException("Username already exists");
        }

        UserPerson user = UserPerson.builder()
                .username(userRequest.username())
                .passwordHash(passwordEncoder.encode(userRequest.password()))
                .role(userRequest.role())
                .active(true)
                .build();

        UserPerson saveUser = userRepository.save(user);

        return toResponseUser(saveUser);
    }

    @Override
    public UserResponse updateUser(Integer id, UserUpdateRequest userRequest) {

        UserPerson user = userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User not found."));

        if( userRepository.existsByUsername(userRequest.username()) ) {
            throw new ResourceNotFoundException("Username already exists.");
        }

        user.setName(userRequest.name());
        user.setUsername(userRequest.username());
        user.setRole(userRequest.role());

        if (userRequest.active() != null) {
            user.setActive(userRequest.active());
        }

        UserPerson updatedUser = userRepository.save(user);

        return toResponseUser(updatedUser);
    }

    @Override
    public void deleteUser(Integer id) {

        if( !userRepository.existsById(id) ) {
            throw new ResourceNotFoundException("User not found.");
        }
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> listUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(this::toResponseUser)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(Integer id) {
        UserPerson user = userRepository.findById(id)
                .orElseThrow( () ->
                        new ResourceNotFoundException("User not found.") );
        return toResponseUser(user);
    }


    private UserResponse toResponseUser(UserPerson user) {

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getRole(),
                user.getActive(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }


}

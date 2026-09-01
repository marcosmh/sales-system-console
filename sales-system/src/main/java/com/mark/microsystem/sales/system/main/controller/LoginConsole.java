package com.mark.microsystem.sales.system.main.controller;

import com.mark.microsystem.sales.system.main.model.entity.UserPerson;
import com.mark.microsystem.sales.system.main.repository.UserPersonRepository;
import com.mark.microsystem.sales.system.main.utils.ConsoleColors;
import com.mark.microsystem.sales.system.main.utils.LoginBox;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class LoginConsole {

    private final UserPersonRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    ConsoleColors colors = new ConsoleColors();
    LoginBox box = new LoginBox(colors);

    public UserPerson login() {

        Map<String, String> results = box.showLoginBox(50);

        String username = results.get("USERNAME");
        String password = results.get("PASSWORD");

        return userRepository.findByUsername(username)
                .filter(u -> passwordEncoder.matches(password, u.getPasswordHash()))
                .orElseThrow( () -> new RuntimeException(colors.red("Invalid Credentials.")));


    }


}

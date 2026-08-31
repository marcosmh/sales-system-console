package com.mark.microsystem.sales.system.main.controller;

import com.mark.microsystem.sales.system.main.model.entity.UserPerson;
import com.mark.microsystem.sales.system.main.repository.UserPersonRepository;
import lombok.RequiredArgsConstructor;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginConsole {

    private final UserPersonRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserPerson login() {
        TextIO textIO = TextIoFactory.getTextIO();
        String username = textIO.newStringInputReader().read("User:");
        String password = textIO.newStringInputReader().withInputMasking(true).read("Password:");

        return userRepository.findByUsername(username)
                .filter(u -> passwordEncoder.matches(password, u.getPasswordHash()))
                .orElseThrow(() -> new RuntimeException("Invalid Credentials."));
    }


}

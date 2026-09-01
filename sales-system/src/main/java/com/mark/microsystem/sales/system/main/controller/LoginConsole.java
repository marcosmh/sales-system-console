package com.mark.microsystem.sales.system.main.controller;

import com.mark.microsystem.sales.system.main.model.entity.UserPerson;
import com.mark.microsystem.sales.system.main.repository.UserPersonRepository;
import com.mark.microsystem.sales.system.main.utils.ConsoleColors;
import com.mark.microsystem.sales.system.main.utils.LoginBox;
import lombok.RequiredArgsConstructor;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
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

        /*
        TextIO textIO = TextIoFactory.getTextIO();

        box.printCenteredBox("SALES SYSTEM LOGIN", 50);

        Map<String, Boolean> fields = new LinkedHashMap<>();
        fields.put("USERNAME+++", false);
        fields.put("PASSWORD+++", true);

        Map<String, String> results = box.inputBox(fields, 50);

        String username = results.get("USERNAME***");
        String password = results.get("PASSWORD***");
        */

        // String username = textIO.newStringInputReader().read(colors.green("User:"));
        // String password = textIO.newStringInputReader().withInputMasking(true).read(colors.green("Password:"));

        return userRepository.findByUsername(username)
                .filter(u -> passwordEncoder.matches(password, u.getPasswordHash()))
                .orElseThrow(() -> new RuntimeException(colors.red("Invalid Credentials.")));
    }


}

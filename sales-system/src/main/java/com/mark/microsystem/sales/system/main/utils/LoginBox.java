package com.mark.microsystem.sales.system.main.utils;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class LoginBox {

    private final ConsoleColors colors;

    // Constructor: you can inject colors
    public LoginBox(ConsoleColors colors) {
        this.colors = colors;
    }

    // Method to print centered box
    public Map<String, String> showLoginBox(int width) {
        TextIO textIO = TextIoFactory.getTextIO();
        Map<String, String> results = new LinkedHashMap<>();

        // Borde superior
        String border = colors.green("+" + "-".repeat(width - 2) + "+");
        System.out.println(border);

        // Título centrado
        String title = "SALES SYSTEM LOGIN";
        String paddingTitle = " ".repeat((width - title.length() - 2) / 2);
        System.out.println(colors.green("|" + paddingTitle + title + paddingTitle + "|"));

        // Separador
        System.out.println(colors.green("|" + "-".repeat(width - 2) + "|"));

        // Línea vacía
        System.out.println(colors.green("|" + " ".repeat(width - 2) + "|"));

        // USERNAME input
        String usernameLabel = "USERNAME: ";
        String username = textIO.newStringInputReader().read(colors.green(usernameLabel));
        String usernameLine = usernameLabel + username;
        results.put("USERNAME", username);

        // Línea vacía
        System.out.println(colors.green("|" + " ".repeat(width - 2) + "|"));

        // PASSWORD input
        String passwordLabel = "PASSWORD: ";
        String password = textIO.newStringInputReader().withInputMasking(true).read(colors.green(passwordLabel));
        String passwordLine = passwordLabel + "*".repeat(password.length());
        results.put("PASSWORD", password);

        // Línea vacía
        System.out.println(colors.green("|" + " ".repeat(width - 2) + "|"));

        // Borde inferior
        System.out.println(border);

        return results;
    }



}
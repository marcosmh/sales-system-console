package com.mark.microsystem.sales.system.main.controller;

import com.mark.microsystem.sales.system.main.model.dto.UserCreateRequest;
import com.mark.microsystem.sales.system.main.model.dto.UserResponse;
import com.mark.microsystem.sales.system.main.model.dto.UserUpdateRequest;
import com.mark.microsystem.sales.system.main.service.IUserService;
import com.mark.microsystem.sales.system.main.utils.ConsoleColors;
import com.mark.microsystem.sales.system.main.utils.ConsoleUtils;

import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UserConsole {

    private final PasswordEncoder passwordEncoder;

    private final IUserService userService;
    private final ConsoleColors colors = new ConsoleColors();
    private final ConsoleUtils consoleUtils = new ConsoleUtils(colors);


    public void menuUsers() {
        consoleUtils.clearScreen();
        TextIO textIO = TextIoFactory.getTextIO();
        int option = textIO.newIntInputReader()
                .withMinVal(1)
                .withMaxVal(6)
                .read(colors.yellow("User Management:\n")
                        + colors.green("1. Create User\n")
                        + colors.blue("2. Find User for Id\n")
                        + colors.purple("3. List Users\n")
                        + colors.cyan("4. Updated User\n")
                        + colors.orange("5. Delete User\n")
                        + colors.red("6. Back\n")
                );

        switch (option) {
            case 1 -> createUser(textIO);
            case 2 -> findUserForId(textIO);
            case 3 -> listUser(textIO);
            case 4 -> updateUser(textIO);
            case 5 -> deleteUser(textIO);
            case 6 -> { return; }
            default -> System.out.println( colors.red("Invalid option.") );
        }

    }

    private void createUser(TextIO textIO) {

        System.out.print(colors.blue("\n Create User \n"));

        String name = textIO.newStringInputReader()
                .withValueChecker( (value, item) -> {
                    System.out.println("Name: "+  " value: " + value);
                    if ( value == null || value.isBlank() ) {
                        throw new IllegalArgumentException("Name cannot by empty");
                    }
                    return Collections.emptyList();
                })
                .read("Name: ");

        String username = textIO.newStringInputReader()
                .withValueChecker( (value, item) -> {
                    System.out.println("Username: "+  " value: " + value);
                    if ( value == null || value.isBlank() ) {
                        throw new IllegalArgumentException("Username cannot by empty");
                    }
                    return Collections.emptyList();
                })
                .read("User Name: ");


        String password = textIO.newStringInputReader()
                .withValueChecker( (value, item) -> {
                    if ( value == null || value.isBlank() ) {
                        throw new IllegalArgumentException("Password cannot by empty");
                    }
                    return Collections.emptyList();
                })
                .read("Password: ");


        String role = textIO.newStringInputReader()
                .withPossibleValues("ADMIN","SELLER")
                .read("Role: ");

        String passwordHash = passwordEncoder.encode(password);

        UserCreateRequest userRequest = new UserCreateRequest(name, username, passwordHash, role, true);
        System.out.println(userRequest);

        try {
            UserResponse user = userService.createUser(userRequest);
            System.out.println(colors.green("\n User created successfully") );
            printUser(user);
            consoleUtils.pause(textIO);
        } catch (Exception e) {
            System.out.println( colors.red("\n Error creating user: " +  e.getMessage()) );
            consoleUtils.pause(textIO);
        }



    }

    private void findUserForId(TextIO textIO) {
        System.out.print(colors.blue("\n Find User \n"));

        Integer id = textIO.newIntInputReader()
                .withMinVal(1)
                .read("User Id: ");

        try {
            UserResponse user = userService.getUserById(id);
            printUser(user);
            consoleUtils.pause(textIO);
        } catch (Exception e) {
            System.out.println( colors.red("\n Error searching for the  user: " +  e.getMessage()) );
            consoleUtils.pause(textIO);
        }

    }

    private void listUser(TextIO textIO) {
        System.out.print(colors.blue("\n Users \n"));

        try {

            List<UserResponse> users = userService.listUsers();
            if(users.isEmpty()) {
                System.out.print(colors.orange("\n No users found."));
                return;
            }

            System.out.println( colors.cyan( String.format( "%-5s %-25s %-20s %-10s %-10s", "ID", "NAME", "USERNAME", "ROLE", "ACTIVE" ) ) );
            System.out.println( colors.cyan( "--------------------------------------------------------------------------" ) );
            users.forEach(this::printUserRow);
            consoleUtils.pause(textIO);

        } catch (Exception e) {
            System.out.println( colors.red( "\nError listing users: " + e.getMessage() ) );
            consoleUtils.pause(textIO);
        }



    }

    private void updateUser(TextIO textIO) {
        System.out.print(colors.blue("\n Update User \n"));

        String existUsername = textIO.newStringInputReader()
                .withValueChecker( (value, item) -> {
                    System.out.println("Username: "+  " value: " + value);
                    if ( value == null || value.isBlank() ) {
                        throw new IllegalArgumentException(colors.orange("Username cannot by empty"));
                    }
                    return Collections.emptyList();
                })
                .read("Username: ");

        try {
            UserResponse currentUser = userService.getUserByName(existUsername);

            System.out.println( colors.yellow("\nCurrent user:") ); printUser(currentUser);

            String name = textIO.newStringInputReader() .read( "Name [" + currentUser.name() + "]: " );

            String username = textIO.newStringInputReader() .read( "Username [" + currentUser.username() + "]: " );

            String role = textIO.newStringInputReader() .withPossibleValues("ADMIN", "SELLER") .read( "Role [" + currentUser.role() + "]: " );
            Boolean active = textIO.newBooleanInputReader() .read( "Active [" + currentUser.active() + "]: " );

            if(currentUser.username().equalsIgnoreCase(username)) {
                throw new IllegalArgumentException(colors.orange("Username already exists."));
            }

            UserUpdateRequest request = new UserUpdateRequest( name, username, role, active );

            UserResponse updatedUser = userService.updateUser(currentUser.id(), request);

            System.out.println( colors.green( "\nUser updated successfully!" ) );

            printUser(updatedUser);
            consoleUtils.pause(textIO);


        } catch (Exception e) {
            System.out.println( colors.red( "\nError updating user: " + e.getMessage() ) );
            consoleUtils.pause(textIO);
        }


    }

    private void deleteUser(TextIO textIO) {
        System.out.print(colors.blue("\n Delete User \n"));

        // Integer id = textIO.newIntInputReader() .withMinVal(1) .read("User ID: ");

        String existUsername = textIO.newStringInputReader()
                .read("Username: ");

        try {

            // UserResponse user = userService.getUserById(id);
            UserResponse user = userService.getUserByName(existUsername);
            System.out.println( colors.yellow("\nUser to delete:") );
            printUser(user);

            if( !existUsername.equalsIgnoreCase(user.username())) {
                throw new IllegalArgumentException(colors.orange("Username not exists."));
            }

            boolean confirm = textIO.newBooleanInputReader().read( colors.red( "Are you sure you want to delete this user?" ) );

            if (!confirm) {
                System.out.println(colors.yellow("Operation cancelled."));
                return;
            }

            userService.deleteUser(user.id());
            System.out.println( colors.green( "\nUser deleted successfully!" ) );
            consoleUtils.pause(textIO);


        } catch (Exception e) {
            System.out.println( colors.red( "\nError delete user: " + e.getMessage() ) );
            consoleUtils.pause(textIO);
        }


    }

    private void printUser(UserResponse user) {
        System.out.println();
        System.out.println(colors.cyan("ID: ") + user.id());
        System.out.println(colors.cyan("Name: ") + user.name());
        System.out.println(colors.cyan("Username: ") + user.username());
        System.out.println(colors.cyan("Role: ") + user.role());
        System.out.println(colors.cyan("Active: ") + user.active());
        System.out.println(colors.cyan("Created: ") + user.createdAt());
        System.out.println(colors.cyan("Updated: ") + user.updatedAt());
    }

    private void printUserRow(UserResponse user) {
        System.out.printf("%-5s %-25s %-20s %-10s %-10s%n", user.id(), user.name(), user.username(), user.role(), user.active());
    }




}

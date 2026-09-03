package com.mark.microsystem.sales.system;

import com.mark.microsystem.sales.system.main.controller.LoginConsole;

import com.mark.microsystem.sales.system.main.controller.UserConsole;
import com.mark.microsystem.sales.system.main.menu.MainMenu;
import com.mark.microsystem.sales.system.main.model.entity.UserPerson;
import com.mark.microsystem.sales.system.main.utils.ConsoleColors;
import com.mark.microsystem.sales.system.main.utils.ConsoleUtils;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SalesSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalesSystemApplication.class, args);
	}

	@Bean
	CommandLineRunner runMenu(LoginConsole loginConsole, UserConsole userConsole) {

		return args -> {

			ConsoleColors colors = new ConsoleColors();
			ConsoleUtils consoleUtils = new ConsoleUtils(colors);
			TextIO textIO = TextIoFactory.getTextIO();
			consoleUtils.clearScreen();

			UserPerson user;
			try {
				user = loginConsole.login();
				consoleUtils.clearScreen();
			} catch (RuntimeException e) {
				textIO.getTextTerminal().println(colors.red("Invalid credentials. Exiting..."));
				System.exit(1);
				return;
			}

			textIO.getTextTerminal().println(
					colors.green("Welcome " + user.getUsername() + " with role " + user.getRole()));

			MainMenu mainMenu = new MainMenu(loginConsole, userConsole, colors, consoleUtils);
			mainMenu.showMenu(user, textIO);

		};

	}

}

package com.mark.microsystem.sales.system;

import com.mark.microsystem.sales.system.main.controller.LoginConsole;

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
	CommandLineRunner runMenu(LoginConsole loginConsole) {

		ConsoleColors colors = new ConsoleColors();
		ConsoleUtils consoleUtils = new ConsoleUtils(colors);
		consoleUtils.clearScreen();

		return args -> {

		TextIO textIO = TextIoFactory.getTextIO();
		UserPerson user;

		try {
			user = loginConsole.login();
		} catch (RuntimeException e) {
			textIO.getTextTerminal().println("\u001B[31mInvalid credentials. Exiting...\u001B[0m");
			System.exit(1);
			return;
		}

		textIO.getTextTerminal().println(
				"\u001B[32mWelcome " + user.getUsername() + " with role " + user.getRole() + "\u001B[0m"
		);

		long lastActivity = System.currentTimeMillis();

		while (true) {

			if (System.currentTimeMillis() - lastActivity > 5 * 60 * 1000) {
				textIO.getTextTerminal().println("\u001B[31mSession expired. Please login again.\u001B[0m");
				System.exit(0);
			}

			textIO.getTextTerminal().println(colors.cyan("=== SALES SYSTEM ==="));

			int opcion = textIO.newIntInputReader()
					.withMinVal(1)
					.withMaxVal(5)
					.read(colors.yellow("Select an option:\n")
							+ colors.green("1. Login\n")
							+ colors.blue("2. Register Sale\n")
							+ colors.purple("3. Inventories\n")
							+ colors.cyan("4. Box Cut\n")
							+ colors.red("5. Exit"));


			switch(opcion) {
				case 1: // Login
					user = loginConsole.login();
					textIO.getTextTerminal().println(colors.green( "Welcome " + user.getUsername() + " with rol " + user.getRole()));
					break;
				case 2: // Ventas
					break;
				case 3: // Inventarios
					break;
				case 4: // Corte de Caja
					break;
				case 5:
					textIO.getTextTerminal().println(colors.green("Leaving the system..."));
					System.exit(0);
					break;
				default:
					textIO.getTextTerminal().println(colors.red("Invalid option."));

			}

		}

	};


	}

}

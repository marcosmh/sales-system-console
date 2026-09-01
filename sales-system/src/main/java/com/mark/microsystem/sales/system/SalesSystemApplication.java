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
			consoleUtils.clearScreen();
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

			consoleUtils.clearScreen();
			textIO.getTextTerminal().println(colors.cyan("=== SALES SYSTEM ==="));

			if(user.getRole().equalsIgnoreCase("ADMIN")) {
				showAdminMenu(loginConsole, textIO, colors, consoleUtils);
			} else {
				showSellerMenu(textIO, colors, consoleUtils);
			}

		}

	};


	}

	private static void showAdminMenu(LoginConsole loginConsole, TextIO textIO, ConsoleColors colors, ConsoleUtils consoleUtils) {
		UserPerson user;
		int opcion = textIO.newIntInputReader()
				.withMinVal(1)
				.withMaxVal(7)
				.read(colors.yellow("Select an option:\n")
						+ colors.green("1. Users\n")
						+ colors.blue("2. Inventory\n")
						+ colors.purple("3. Sales\n")
						+ colors.cyan("4. Cash Reconciliation\n")
						+ colors.cyan("5. Suppliers\n")
						+ colors.cyan("6. Invoices\n")
						+ colors.red("7. Exit"));


		switch(opcion) {
			case 1: // Users
				// user = loginConsole.login();
				// textIO.getTextTerminal().println(colors.green( "Welcome " + user.getUsername() + " with rol " + user.getRole()));
				break;
			case 2: // Inventory
				break;
			case 3: // Sales
				break;
			case 4: // Cash Reconciliation
				break;
			case 5: // Suppliers
				break;
			case 6: // Invoices
				break;
			case 7:
				// textIO.getTextTerminal().println(colors.green("Leaving the system..."));
				consoleUtils.clearScreen();
				System.exit(0);
				break;
			default:
				textIO.getTextTerminal().println(colors.red("Invalid option."));

		}
	}

	private static void showSellerMenu(TextIO textIO, ConsoleColors colors, ConsoleUtils consoleUtils) {
		UserPerson user;
		int opcion = textIO.newIntInputReader()
				.withMinVal(1)
				.withMaxVal(4)
				.read(colors.yellow("Select an option:\n")
						+ colors.green("1. Inventory\n")
						+ colors.blue("2. Sales\n")
						+ colors.purple("3. Invoices\n")
						+ colors.red("4. Exit"));


		switch(opcion) {
			case 1: // Inventory
				break;
			case 2: // Sales
				break;
			case 3: // Invoices
				break;
			case 4:
				// textIO.getTextTerminal().println(colors.green("Exit the seller menu..."));
				consoleUtils.clearScreen();
				System.exit(0);
				break;
			default:
				textIO.getTextTerminal().println(colors.red("Invalid option."));

		}
	}



}

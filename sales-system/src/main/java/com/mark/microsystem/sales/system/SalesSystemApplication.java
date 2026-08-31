package com.mark.microsystem.sales.system;

import com.mark.microsystem.sales.system.main.controller.LoginConsole;

import com.mark.microsystem.sales.system.main.utils.ConsoleColors;
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

		clearScreen();

		ConsoleColors colors = new ConsoleColors();

		return args -> {

		TextIO textIO = TextIoFactory.getTextIO();
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
				var user = loginConsole.login();
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


	};


	}

	public static void clearScreen() {
		try {
			if (System.getProperty("os.name").startsWith("Windows")) {
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			} else {
				new ProcessBuilder("clear").inheritIO().start().waitFor();
			}
		} catch (Exception e) {
			for (int i = 0; i < 50; i++) System.out.println();
		}
	}


}

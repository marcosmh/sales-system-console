package com.mark.microsystem.sales.system;

import com.mark.microsystem.sales.system.main.controller.LoginConsole;

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

		return args -> {

		TextIO textIO = TextIoFactory.getTextIO();
		textIO.getTextTerminal().println("=== SISTEMA DE VENTAS ===");


		int opcion = textIO.newIntInputReader()
				.withMinVal(1)
				.withMaxVal(5)
				.read("Seleccione una opción:\n1. Login\n2. Registrar Venta\n3. Inventarios\n4. Corte de Caja\n5. Salir");

		switch(opcion) {
			case 1: // Login
				var user = loginConsole.login();
				textIO.getTextTerminal().println("Welcome " + user.getUsername() + " with rol " + user.getRole());
				break;
			case 2: // Ventas
				break;
			case 3: // Inventarios
				break;
			case 4: // Corte de Caja
				break;
			case 5: System.exit(0);
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

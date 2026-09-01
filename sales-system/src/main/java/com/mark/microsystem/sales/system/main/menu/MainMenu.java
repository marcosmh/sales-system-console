package com.mark.microsystem.sales.system.main.menu;

import com.mark.microsystem.sales.system.main.controller.LoginConsole;
import com.mark.microsystem.sales.system.main.model.entity.UserPerson;
import com.mark.microsystem.sales.system.main.utils.ConsoleColors;
import com.mark.microsystem.sales.system.main.utils.ConsoleUtils;
import org.beryx.textio.TextIO;

public class MainMenu {

    private final LoginConsole loginConsole;
    private final ConsoleColors colors;
    private final ConsoleUtils consoleUtils;


    public MainMenu(LoginConsole loginConsole, ConsoleColors colors, ConsoleUtils consoleUtils) {
        this.loginConsole = loginConsole;
        this.colors = colors;
        this.consoleUtils = consoleUtils;
    }

    public void showMenu(UserPerson user, TextIO textIO) {
        while (true) {
            consoleUtils.clearScreen();
            textIO.getTextTerminal().println(colors.cyan("=== SALES SYSTEM ==="));

            if (user.getRole().equalsIgnoreCase("ADMIN")) {
                showAdminMenu(textIO);
            } else {
                showSellerMenu(textIO);
            }
        }
    }

    private void showAdminMenu(TextIO textIO) {
        int option = textIO.newIntInputReader()
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

        switch (option) {
            case 1: // Users
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
                consoleUtils.clearScreen();
                System.exit(0);
                break;
            default:
                textIO.getTextTerminal().println(colors.red("Invalid option."));
        }
    }

    private void showSellerMenu(TextIO textIO) {
        int option = textIO.newIntInputReader()
                .withMinVal(1)
                .withMaxVal(4)
                .read(colors.yellow("Select an option:\n")
                        + colors.green("1. Inventory\n")
                        + colors.blue("2. Sales\n")
                        + colors.purple("3. Invoices\n")
                        + colors.red("4. Exit"));

        switch (option) {
            case 1: // Inventory
                break;
            case 2: // Sales
                break;
            case 3: // Invoices
                break;
            case 4:
                consoleUtils.clearScreen();
                System.exit(0);
                break;
            default:
                textIO.getTextTerminal().println(colors.red("Invalid option."));
        }
    }


}

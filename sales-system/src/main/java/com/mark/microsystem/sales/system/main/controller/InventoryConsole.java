package com.mark.microsystem.sales.system.main.controller;

import com.mark.microsystem.sales.system.main.service.IProductService;
import com.mark.microsystem.sales.system.main.service.ISupplier;
import com.mark.microsystem.sales.system.main.utils.ConsoleColors;
import com.mark.microsystem.sales.system.main.utils.ConsoleUtils;
import lombok.RequiredArgsConstructor;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryConsole {

    private final IProductService productService;
    private final ISupplier supplierService;

    private final ConsoleColors colors = new ConsoleColors();
    private final ConsoleUtils consoleUtils = new ConsoleUtils(colors);

    public void menuInventory() {

        consoleUtils.clearScreen();
        boolean continuee = true;

        while (continuee) {

            consoleUtils.clearScreen();
            TextIO textIO = TextIoFactory.getTextIO();
            int option = textIO.newIntInputReader()
                    .withMinVal(1)
                    .withMaxVal(5)
                    .read(colors.yellow("Inventory Management:\n")
                            + colors.green("1. Create Product\n")
                            + colors.blue("2. Create Supplier\n")
                            + colors.purple("3. List Products\n")
                            + colors.cyan("4. List Suppliers\n")
                            + colors.orange("5. Back\n")
                    );

            switch (option) {

                case 1 -> createProduct(textIO);

                case 2 -> createSupplier(textIO);

                case 3 -> listProducts(textIO);

                case 4 -> listSuppliers(textIO);

                case 5 -> {
                    textIO.getTextTerminal()
                            .println(colors.yellow("\nRegresando al menú principal...\n"));
                    continuee = false;
                }
            }

            if (continuee) {
                consoleUtils.pause(textIO);
            }


        }



    }


    private void createProduct(TextIO textIO) {
        System.out.print(colors.blue("\n Create Product \n"));

    }

    private void createSupplier(TextIO textIO) {
        System.out.print(colors.blue("\n Create Supplier \n"));

    }

    private void listProducts(TextIO textIO) {

    }

    private void listSuppliers(TextIO textIO) {

    }




}

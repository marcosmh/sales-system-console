package com.mark.microsystem.sales.system.main.controller;

import com.mark.microsystem.sales.system.main.model.dto.ProductCreateRequest;
import com.mark.microsystem.sales.system.main.model.dto.ProductResponse;
import com.mark.microsystem.sales.system.main.model.dto.SupplierCreateRequest;
import com.mark.microsystem.sales.system.main.model.dto.SupplierResponse;
import com.mark.microsystem.sales.system.main.model.entity.Product;
import com.mark.microsystem.sales.system.main.model.entity.Supplier;
import com.mark.microsystem.sales.system.main.service.IProductService;
import com.mark.microsystem.sales.system.main.service.ISupplier;
import com.mark.microsystem.sales.system.main.utils.ConsoleColors;
import com.mark.microsystem.sales.system.main.utils.ConsoleUtils;
import lombok.RequiredArgsConstructor;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InventoryConsole {

    private final IProductService productService;
    private final ISupplier supplierService;

    private final ConsoleColors colors = new ConsoleColors();
    private final ConsoleUtils consoleUtils = new ConsoleUtils(colors);

    public void menuInventory() {

        consoleUtils.clearScreen();
        boolean repeat = true;

        while (repeat) {

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
                            .println(colors.yellow("\nReturning to the main menu...\n"));
                    repeat = false;
                }
                default -> System.out.println( colors.red("Invalid option.") );
            }

            if (repeat) {
                consoleUtils.pause(textIO);
            }

        }

    }


    private void createProduct(TextIO textIO) {

        System.out.print(colors.blue("\n === Create Product === \n"));

        String name = textIO.newStringInputReader()
                .withValueChecker( (value, item) -> {
                    if ( value == null || value.isBlank() ) {
                        throw new IllegalArgumentException("Name cannot by empty");
                    }
                    return Collections.emptyList();
                })
                .read( colors.yellowLight("Name: "));

        String description = textIO.newStringInputReader()
                .withValueChecker( (value, item) -> {
                    if ( value == null || value.isBlank() ) {
                        throw new IllegalArgumentException("Description cannot by empty");
                    }
                    return Collections.emptyList();
                })
                .read(colors.yellowLight("Description: "));

        BigDecimal price = new BigDecimal(textIO.newStringInputReader()
                .withMinLength(1)
                .withValueChecker( (value, item) -> {
                    if ( value == null ) {
                        throw new IllegalArgumentException("Price cannot by empty");
                    }
                    return Collections.emptyList();
                })
                .read(colors.yellowLight("Price: ")));

        Integer stock = textIO.newIntInputReader()
                .withMinVal(0)
                .read(colors.yellowLight("Stock: "));

        Integer supplierId = textIO.newIntInputReader()
                .withMinVal(1)
                .read(colors.yellowLight("Supplier Id "));

        ProductCreateRequest productReq = new ProductCreateRequest(name, description, price, stock, supplierId);

        try {
            ProductResponse product = productService.createProducto(productReq);
            System.out.println(colors.green("\n Successfully created product.") );
            showProduct(textIO, product);

        }  catch(Exception e) {
            System.out.println(colors.red("\n Error creating product: ") + e.getMessage() );
            consoleUtils.pause(textIO);
        }


    }

    private void createSupplier(TextIO textIO) {
        System.out.print(colors.blue("\n Create Supplier \n"));

        String name = textIO.newStringInputReader()
                .withValueChecker( (value, item) -> {
                    if ( value == null || value.isBlank() ) {
                        throw new IllegalArgumentException("Name cannot by empty");
                    }
                    return Collections.emptyList();
                })
                .read( colors.yellowLight("Name: "));

        String contact = textIO.newStringInputReader()
                .withValueChecker( (value, item) -> {
                    if ( value == null || value.isBlank() ) {
                        throw new IllegalArgumentException("Contact cannot by empty");
                    }
                    return Collections.emptyList();
                })
                .read( colors.yellowLight("Contact: "));


        String phone = textIO.newStringInputReader()
                .withValueChecker( (value, item) -> {
                    if ( value == null || value.isBlank() ) {
                        throw new IllegalArgumentException("Phone cannot by empty");
                    }
                    return Collections.emptyList();
                })
                .read( colors.yellowLight("Phone: "));

        String email = textIO.newStringInputReader()
                .withValueChecker( (value, item) -> {
                    if ( value == null || value.isBlank() ) {
                        throw new IllegalArgumentException("Email cannot by empty");
                    }
                    return Collections.emptyList();
                })
                .read( colors.yellowLight("Email: "));

        SupplierCreateRequest request = new SupplierCreateRequest(name, contact, phone, email);

        try {
            SupplierResponse supplier = supplierService.createSupplier(request);
            System.out.println(colors.green("\n Successfully created supplier.") );
            showSupplier(textIO,supplier);
        }  catch(Exception e) {
            System.out.println(colors.red("\n Error creating supplier: ") + e.getMessage() );
            consoleUtils.pause(textIO);
        }

    }

    private void listProducts(TextIO textIO) {
        System.out.print(colors.blue("\n List Products \n"));

        try {
            List<ProductResponse> products = productService.listProductos();
            if(products.isEmpty()) {
                System.out.print(colors.orange("\n No products found."));
                return;
            }

            for(ProductResponse product : products) {
                showProduct(textIO, product);
            }

        } catch (Exception e) {
            System.out.println(colors.red("\n Error show the list products: ") + e.getMessage() );
            consoleUtils.pause(textIO);
        }

    }

    private void listSuppliers(TextIO textIO) {

        System.out.print(colors.blue("\n List Suppliers \n"));

        try {
            List<SupplierResponse> suppliers = supplierService.listSuppliers();
            if(suppliers.isEmpty()) {
                System.out.print(colors.orange("\n No suppliers found."));
                return;
            }

            for(SupplierResponse supplier : suppliers) {
                showSupplier(textIO, supplier);
            }

        } catch (Exception e) {
            System.out.println(colors.red("\n Error show the list suppliers: ") + e.getMessage() );
            consoleUtils.pause(textIO);
        }

    }

    private void showProduct(TextIO textIO, ProductResponse product) {

        System.out.println();
        String supplierName = product.supplier() != null
                ? product.supplier().name()
                : "Sin proveedor";

        System.out.println( colors.cyan( String.format( "%-5s %-25s %-20s %-10s %-10s", "ID", "NAME", "PRICE", "STOCK", "SUPPLIER" )));
        System.out.println( colors.cyan( "--------------------------------------------------------------------------" ) );
        System.out.printf(  colors.pinkLight("%-5s %-25s %-20s %-10s %-10s%n"),  product.id(), product.name(), product.price(), product.stock(), product.supplier().name());

    }

    private void showSupplier(TextIO textIO, SupplierResponse supplier) {

        System.out.println();
        System.out.println( colors.cyan( String.format( "%-5s %-25s %-20s %-10s %-10s",
                "ID", "NAME", "CONTACT", "PHONE", "EMAIL" )));
        System.out.println( colors.cyan( "--------------------------------------------------------------------------" ) );
        System.out.printf(  colors.pinkLight("%-5s %-25s %-20s %-10s %-10s%n"),
                supplier.id(), supplier.name(), supplier.contact(), supplier.phone(), supplier.email());

    }



}

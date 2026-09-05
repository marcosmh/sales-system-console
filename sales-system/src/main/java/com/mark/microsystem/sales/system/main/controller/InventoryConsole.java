package com.mark.microsystem.sales.system.main.controller;

import com.mark.microsystem.sales.system.main.model.dto.*;
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
                    .withMaxVal(3)
                    .read(colors.yellow("Inventory Management:\n")
                            + colors.green("1. Product Menu\n")
                            + colors.blue("2. Supplier Menu\n")
                            + colors.orange("3. Back\n"));

            switch (option) {

                case 1 -> productMenu(textIO);

                case 2 -> supplierMenu(textIO);

                case 3 -> {
                    textIO.getTextTerminal().println(colors.yellow("\nReturning to the main menu...\n"));
                    repeat = false;
                }
                default -> System.out.println( colors.red("Invalid option.") );
            }
            if (repeat) consoleUtils.pause(textIO);

        }

    }

    private void productMenu(TextIO textIO) {
        boolean repeat = true;
        while (repeat) {
            consoleUtils.clearScreen();
            int option = textIO.newIntInputReader()
                    .withMinVal(1)
                    .withMaxVal(6)
                    .read(colors.brightBlue("Product Management:\n")
                            + colors.brightPurple("1. Create Product\n")
                            + colors.brightPurple("2. Find Product by Id\n")
                            + colors.brightPurple("3. List Products\n")
                            + colors.brightPurple("4. Update Product\n")
                            + colors.brightPurple("5. Delete Product\n")
                            + colors.brightPurple("6. Back\n"));

            switch (option) {
                case 1 -> createProduct(textIO);
                case 2 -> findProductById(textIO);
                case 3 -> listProducts(textIO);
                case 4 -> updateProduct(textIO);
                case 5 -> deleteProduct(textIO);
                case 6 -> repeat = false;
                default -> textIO.getTextTerminal().println(colors.red("Invalid option."));
            }
            if (repeat) consoleUtils.pause(textIO);
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
            showTitleProduct();
            showProduct(product);

        }  catch(Exception e) {
            System.out.println(colors.red("\n Error creating product: ") + e.getMessage() );
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
            showTitleProduct();
            for(ProductResponse product : products) {
                showProduct(product);
            }
        } catch (Exception e) {
            System.out.println(colors.red("\n Error show the list products: ") + e.getMessage() );
            consoleUtils.pause(textIO);
        }
    }

    public void findProductById(TextIO textIO) {
        System.out.print(colors.blue("\n Find Product for Id \n"));
        Integer id = textIO.newIntInputReader()
                .withMinVal(1)
                .read(colors.yellowLight("Product Id: "));
        try {
            ProductResponse product = productService.getProductoById(id);
            showTitleProduct();
            showProduct(product);
        } catch (Exception e) {
            System.out.println( colors.red("\n Error searching for the  product: " +  e.getMessage()) );
            consoleUtils.pause(textIO);
        }

    }

    public void updateProduct(TextIO textIO) {
        System.out.print(colors.blue("\n Update Product \n"));
        String existProduct = textIO.newStringInputReader()
                .withValueChecker( (value, item) -> {
                    if ( value == null || value.isBlank() ) {
                        throw new IllegalArgumentException(colors.orange("Product Name cannot by empty"));
                    }
                    return Collections.emptyList();
                })
                .read(colors.yellowLight("Product Name: "));

        System.out.println("Product: "+existProduct);

        try {
            ProductResponse currentProduct = productService.findByName(existProduct);
            System.out.println( colors.yellow("\nCurrent Product:") );
            showTitleProduct();
            showProduct(currentProduct);

            String name = textIO.newStringInputReader().read( colors.yellowLight("Name [" + currentProduct.name() + "]: ") );
            String description = textIO.newStringInputReader().read( colors.yellowLight("Description [" + currentProduct.description() + "]: ") );
            BigDecimal price = new BigDecimal(textIO.newStringInputReader()
                    .withMinLength(1)
                    .withValueChecker( (value, item) -> {
                        if ( value == null ) {
                            throw new IllegalArgumentException("Price cannot by empty");
                        }
                        return Collections.emptyList();
                    })
                    .read(colors.yellowLight("Price [" + currentProduct.price() + "]: ") ));
            Integer stock = textIO.newIntInputReader().withMinVal(0).read( colors.yellowLight("Stock [" + currentProduct.stock() + "]: ") );
            Integer supplierId = textIO.newIntInputReader().withMinVal(1).read(colors.yellowLight("SupplierId [" + currentProduct.supplier().id() + "]: " ));

            ProductUpdateRequest request = new ProductUpdateRequest(name, description, price, stock, supplierId);
            ProductResponse updatedProduct = productService.updateProducto(currentProduct.id(), request);

            System.out.println( colors.green( "\nProduct updated successfully!" ) );
            showTitleProduct();
            showProduct(updatedProduct);

        } catch (Exception e) {
            System.out.println( colors.red( "\nError updating product: " + e.getMessage() ) );
            consoleUtils.pause(textIO);
        }

    }

    public void deleteProduct(TextIO textIO) {

        System.out.print(colors.blue("\n Delete Product \n"));

        String existProduct = textIO.newStringInputReader()
                .read(colors.yellowLight("Product Name: "));

        try {

            ProductResponse product = productService.findByName(existProduct);
            System.out.println( colors.yellow("\nProduct to delete:") );
            showTitleProduct();
            showProduct(product);

            if( !existProduct.equalsIgnoreCase(product.name())) {
                throw new IllegalArgumentException(colors.orange("Product not exists."));
            }

            boolean confirm = textIO.newBooleanInputReader().read( colors.red( "Are you sure you want to delete this product?" ) );

            if (!confirm) {
                System.out.println(colors.yellow("Operation cancelled."));
                return;
            }

            productService.deleteProducto(product.id());
            System.out.println( colors.green( "\nProduct deleted successfully!" ) );

        } catch (Exception e) {
            System.out.println( colors.red( "\nError delete product: " + e.getMessage() ) );
            consoleUtils.pause(textIO);
        }

    }

    private void supplierMenu(TextIO textIO) {
        boolean repeat = true;
        while (repeat) {
            consoleUtils.clearScreen();
            int option = textIO.newIntInputReader()
                    .withMinVal(1)
                    .withMaxVal(6)
                    .read(colors.brightBlue("Supplier Management:\n")
                            + colors.brightPurple("1. Create Supplier\n")
                            + colors.brightPurple("2. Find Supplier by Id\n")
                            + colors.brightPurple("3. List Suppliers\n")
                            + colors.brightPurple("4. Update Product\n")
                            + colors.brightPurple("5. Delete Product\n")
                            + colors.red("6. Back\n"));

            switch (option) {
                case 1 -> createSupplier(textIO);
                case 2 -> findSuppliertById(textIO);
                case 3 -> listSuppliers(textIO);
                case 4 -> updateSupplier(textIO);
                case 5 -> deleteSupplier(textIO);
                case 6 -> repeat = false;
                default -> textIO.getTextTerminal().println(colors.red("Invalid option."));
            }
            if (repeat) consoleUtils.pause(textIO);
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
            showTitleSupplier();
            showSupplier(supplier);
        }  catch(Exception e) {
            System.out.println(colors.red("\n Error creating supplier: ") + e.getMessage() );
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
            showTitleSupplier();
            for(SupplierResponse supplier : suppliers) {
                showSupplier(supplier);
            }
        } catch (Exception e) {
            System.out.println(colors.red("\n Error show the list suppliers: ") + e.getMessage() );
            consoleUtils.pause(textIO);
        }
    }

    private void findSuppliertById(TextIO textIO) {
        System.out.print(colors.blue("\n Find Supplier for Id \n"));
        Integer id = textIO.newIntInputReader()
                .withMinVal(1)
                .read(colors.yellowLight("Supplier Id: "));
        try {
            SupplierResponse supplier = supplierService.getSupplierById(id);
            showTitleSupplier();
            showSupplier(supplier);
        } catch (Exception e) {
            System.out.println( colors.red("\n Error searching for the  supplier: " +  e.getMessage()) );
            consoleUtils.pause(textIO);
        }
    }

    private void updateSupplier(TextIO textIO) {
        System.out.print(colors.blue("\n Supplier Product \n"));

        String existSupplier = textIO.newStringInputReader()
                .withValueChecker( (value, item) -> {
                    if ( value == null || value.isBlank() ) {
                        throw new IllegalArgumentException(colors.orange("Supplier Name cannot by empty"));
                    }
                    return Collections.emptyList();
                })
                .read(colors.yellowLight("Supplier Name: "));

        try {
            SupplierResponse currentSupplier = supplierService.findByName(existSupplier);

            System.out.println( colors.yellow("\nCurrent Supplier:") );
            showTitleSupplier();
            showSupplier(currentSupplier);

            String name = textIO.newStringInputReader().read( colors.yellowLight("Name [" + currentSupplier.name() + "]: ") );
            String contact = textIO.newStringInputReader().read( colors.yellowLight("Contact [" + currentSupplier.contact() + "]: ") );
            String phone = textIO.newStringInputReader().read( colors.yellowLight("Phone [" + currentSupplier.phone() + "]: ") );
            String email = textIO.newStringInputReader().read( colors.yellowLight("Email [" + currentSupplier.email() + "]: ") );

            SupplierUpdateRequest request = new SupplierUpdateRequest(name, contact, phone, email);
            SupplierResponse updatedSupplier = supplierService.updateSupplier(currentSupplier.id(), request);

            System.out.println( colors.green( "\nSupplier updated successfully!" ) );
            showTitleSupplier();
            showSupplier(updatedSupplier);

        } catch (Exception e) {
            System.out.println( colors.red( "\nError updating supplier: " + e.getMessage() ) );
            consoleUtils.pause(textIO);
        }


    }

    private void deleteSupplier(TextIO textIO) {
        System.out.print(colors.blue("\n Delete Supplier \n"));

        String existSupplier = textIO.newStringInputReader()
                .read(colors.yellowLight("Supplier Name: "));

        try {
            SupplierResponse supplier = supplierService.findByName(existSupplier);
            System.out.println( colors.yellow("\nSupplier to delete:") );
            showTitleSupplier();
            showSupplier(supplier);
            if( !existSupplier.equalsIgnoreCase(supplier.name())) {
                throw new IllegalArgumentException(colors.orange("Supplier not exists."));
            }
            boolean confirm = textIO.newBooleanInputReader().read( colors.red( "Are you sure you want to delete this supplier?" ) );

            if (!confirm) {
                System.out.println(colors.yellow("Operation cancelled."));
                return;
            }
            supplierService.deleteSupplier(supplier.id());
            System.out.println( colors.green( "\nSupplier deleted successfully!" ) );

        } catch (Exception e) {
            System.out.println( colors.red( "\nError delete supplier: " + e.getMessage() ) );
            consoleUtils.pause(textIO);
        }

    }

    private void showTitleProduct() {
        System.out.println( colors.cyan( String.format( "%-5s %-25s %-20s %-10s %-10s", "ID", "NAME", "PRICE", "STOCK", "SUPPLIER" )));
        System.out.println( colors.cyan( "--------------------------------------------------------------------------" ) );
    }

    private void showTitleSupplier() {
        System.out.println( colors.cyan( String.format( "%-5s %-25s %-20s %-10s %-10s",
                "ID", "NAME", "CONTACT", "PHONE", "EMAIL" )));
        System.out.println( colors.cyan( "--------------------------------------------------------------------------" ) );

    }

    private void showProduct(ProductResponse product) {
        System.out.println();
        System.out.printf(  colors.pinkLight("%-5s %-25s %-20s %-10s %-10s%n"),  product.id(), product.name(), product.price(), product.stock(), product.supplier().name());

    }

    private void showSupplier(SupplierResponse supplier) {
        System.out.println();
        System.out.printf(  colors.pinkLight("%-5s %-25s %-20s %-10s %-10s%n"),
                supplier.id(), supplier.name(), supplier.contact(), supplier.phone(), supplier.email());

    }



}

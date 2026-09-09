package com.mark.microsystem.sales.system.main.controller;

import com.mark.microsystem.sales.system.main.model.dto.*;
import com.mark.microsystem.sales.system.main.model.entity.UserPerson;
import com.mark.microsystem.sales.system.main.service.ISaleService;
import com.mark.microsystem.sales.system.main.utils.ConsoleColors;
import com.mark.microsystem.sales.system.main.utils.ConsoleUtils;
import lombok.RequiredArgsConstructor;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SaleConsole {

    private final ISaleService saleService;
    private final ConsoleColors colors = new ConsoleColors();
    private final ConsoleUtils consoleUtils = new ConsoleUtils(colors);

    public void menuSales(UserPerson user) {
        consoleUtils.clearScreen();
        TextIO textIO = TextIoFactory.getTextIO();
        boolean repeat = true;

        System.out.println( colors.green( "\nSales Record" ) );
        List<SaleDetailCreateRequest> details = new ArrayList<>();

        while(repeat) {
            consoleUtils.clearScreen();

            Integer productId = textIO.newIntInputReader()
                    .withMinVal(0)
                    .read(colors.yellowLight("Product ID (O to finish): "));

            if(productId == 0) {
                break;
            }

            Integer quantity = textIO.newIntInputReader()
                    .withMinVal(1)
                    .read(colors.yellowLight("Quantity: "));


            details.add( new SaleDetailCreateRequest(productId, quantity) );

        }

        if(details.isEmpty()) {
            System.out.println( colors.orange( "\nNo products were added to the sale." ) );
            consoleUtils.pause(textIO);
            return;
        }

        SaleCreateRequest request = new SaleCreateRequest(user.getId(), details);

        try {

            SaleResponse sale = saleService.createSale(request);
            consoleUtils.clearScreen();
            printReceipt(sale);
            System.out.println("*****************************");
            printReceipt2(sale,textIO);
            consoleUtils.pause(textIO);
        } catch(RuntimeException e) {
            System.out.println( colors.red( "\nError creating sale: "  + e.getMessage()) );
        }
    }


    private void printReceipt(SaleResponse sale) {
        System.out.println( colors.green( "\nSales Receipt" ) );
        System.out.println( colors.cyan( String.format( "%-5s %-25s %-20s", "SALE ID", "DATE", "SELLER" )));
        System.out.println( colors.cyan( "--------------------------------------------------------------------------" ) );
        System.out.printf(  colors.pinkLight("%-5s %-25s %-20s "),  sale.id(), sale.date(), sale.user().username());
        System.out.println( colors.cyan( "--------------------------------------------------------------------------" ) );

        for(SaleDetailResponse detail : sale.details()) {
            ProductSummaryResponse product = detail.product();
            System.out.printf(  colors.pinkLight(
                    product.name() + " x" + detail.quantity()
                            + " = $" + consoleUtils.formatMoney(detail.subtotal())
            ));
        }

        System.out.println( colors.cyan( "--------------------------------------------------------------------------" ) );

        System.out.printf(  colors.pinkLight( colors.green("TOTAL: $" + consoleUtils.formatMoney(sale.total())) ));

    }

    private void printReceipt2(SaleResponse sale, TextIO textIO) {

        textIO.getTextTerminal().println(
                colors.green("=== SALES RECEIPT ===")
        );

        textIO.getTextTerminal().println(
                "Sale ID: " + sale.id()
        );

        textIO.getTextTerminal().println(
                "Date: " + sale.date()
        );

        textIO.getTextTerminal().println(
                "Seller: " + sale.user().username()
        );

        textIO.getTextTerminal().println(
                "----------------------------------------"
        );

        for (SaleDetailResponse detail : sale.details()) {

            ProductSummaryResponse product =
                    detail.product();

            textIO.getTextTerminal().println(
                    product.name()
                            + " x"
                            + detail.quantity()
                            + " = $"
                            + consoleUtils.formatMoney(detail.subtotal())
            );
        }

        textIO.getTextTerminal().println(
                "----------------------------------------"
        );

        textIO.getTextTerminal().println(
                colors.green(
                        "TOTAL: $"
                                + consoleUtils.formatMoney(sale.total())
                )
        );


    }








}

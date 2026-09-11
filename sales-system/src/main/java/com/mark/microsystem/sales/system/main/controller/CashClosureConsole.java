package com.mark.microsystem.sales.system.main.controller;

import com.mark.microsystem.sales.system.main.model.entity.UserPerson;
import com.mark.microsystem.sales.system.main.service.ICashClosureService;
import com.mark.microsystem.sales.system.main.utils.ConsoleColors;
import com.mark.microsystem.sales.system.main.utils.ConsoleUtils;
import lombok.RequiredArgsConstructor;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CashClosureConsole {

    private final ICashClosureService cashClosureService;
    private final ConsoleColors colors = new ConsoleColors();
    private final ConsoleUtils consoleUtils = new ConsoleUtils(colors);

    public void menuCashClosure(UserPerson user) {
        consoleUtils.clearScreen();
        TextIO textIO = TextIoFactory.getTextIO();

        System.out.println( colors.green( "\nCash Closure" ) );


    }




}

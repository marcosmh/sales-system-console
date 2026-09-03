package com.mark.microsystem.sales.system.main.utils;

import org.beryx.textio.TextIO;

import java.io.IOException;

public class ConsoleUtils {

    private final ConsoleColors colors;

    public ConsoleUtils(ConsoleColors colors) {
        this.colors = colors;
    }


    public void clearScreen() {
        try {
            if (System.getProperty("os.name").startsWith("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("sh","-c","clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }

    public void pause(TextIO textIO) {
        textIO.getTextTerminal()
                .println(colors.yellow("\nPress ENTER to continue..."));

        try {
            System.in.read();
        } catch (IOException e) {
            Thread.currentThread().interrupt();
        }
    }


}

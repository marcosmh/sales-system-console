package com.mark.microsystem.sales.system.main.utils;

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


}

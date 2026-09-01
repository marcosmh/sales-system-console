package com.mark.microsystem.sales.system.main.utils;

public class ConsoleColors {

    // Reset
    public static final String RESET = "\u001B[0m";

    // Basic colors
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    // Methods for brilliant colors
    public static final String BRIGHT_BLACK = "\u001B[90m";
    public static final String BRIGHT_RED = "\u001B[91m";
    public static final String BRIGHT_GREEN = "\u001B[92m";
    public static final String BRIGHT_YELLOW = "\u001B[93m";
    public static final String BRIGHT_BLUE = "\u001B[94m";
    public static final String BRIGHT_PURPLE = "\u001B[95m";
    public static final String BRIGHT_CYAN = "\u001B[96m";
    public static final String BRIGHT_WHITE = "\u001B[97m";

    // Methods for wrapping text
    public static String black(String text) {
        return BLACK + text + RESET;
    }

    public static String red(String text) {
        return RED + text + RESET;
    }

    public static String green(String text) {
        return GREEN + text + RESET;
    }

    public static String yellow(String text) {
        return YELLOW + text + RESET;
    }

    public static String blue(String text) {
        return BLUE + text + RESET;
    }

    public static String purple(String text) {
        return PURPLE + text + RESET;
    }

    public static String cyan(String text) {
        return CYAN + text + RESET;
    }

    public static String white(String text) {
        return WHITE + text + RESET;
    }

    public static String brightBlack(String text) {
        return BRIGHT_BLACK + text + RESET;
    }

    public static String brightRed(String text) {
        return BRIGHT_RED + text + RESET;
    }

    public static String brightGreen(String text) {
        return BRIGHT_GREEN + text + RESET;
    }

    public static String brightYellow(String text) {
        return BRIGHT_YELLOW + text + RESET;
    }

    public static String brightBlue(String text) {
        return BRIGHT_BLUE + text + RESET;
    }

    public static String brightPurple(String text) {
        return BRIGHT_PURPLE + text + RESET;
    }

    public static String brightCyan(String text) {
        return BRIGHT_CYAN + text + RESET;
    }

    public static String brightWhite(String text) {
        return BRIGHT_WHITE + text + RESET;
    }
}

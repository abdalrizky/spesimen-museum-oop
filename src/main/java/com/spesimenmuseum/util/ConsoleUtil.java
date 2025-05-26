package com.spesimenmuseum.util;

public class ConsoleUtil {
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void blueColor() {
        System.out.print("\033[0;34m");
    }

    public static void redColor() {
        System.out.print("\033[0;31m");
    }

    public static void greenColor() {
        System.out.print("\033[0;32m");
    }

    public static void resetColor() {
        System.out.print("\033[0m");
    }
}

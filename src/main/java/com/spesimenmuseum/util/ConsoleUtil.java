package com.spesimenmuseum.util;

import java.util.Scanner;

public class ConsoleUtil {
    private static final Scanner scanner = new Scanner(System.in);

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void delay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Thread sleep dihentikan karena " + e.getMessage());
        }
    }

    public static void pressEnterToContinue() {
        System.out.print("\nTekan Enter Untuk Kembali...");
        scanner.nextLine();
    }

    public static void showErrorMessage(String message) {
        redColor();
        System.out.println("=========================================================================");
        System.out.println("                             ERROR: " + message);
        System.out.println("=========================================================================");
        blueColor();
        delay(1500);
    }

    public static void showSuccessMessage(String message) {
        greenColor();
        System.out.println("=========================================================================");
        System.out.println("                             " + message);
        System.out.println("=========================================================================");
        blueColor();
        delay(1000);
    }

    public static void showInfoMessage(String message) {
        blueColor();
        System.out.println("=========================================================================");
        System.out.println("                             " + message);
        System.out.println("=========================================================================");
        delay(1000);
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

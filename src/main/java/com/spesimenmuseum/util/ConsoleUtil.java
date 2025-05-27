package com.spesimenmuseum.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ConsoleUtil {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final int LINE_WIDTH = 73;

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

    public static String localDateTimeToString(LocalDateTime dateTime) {
        if (dateTime == null) return "-";
        return dateTime.format(dateTimeFormatter);
    }

    public static void pressEnterToContinue() {
        System.out.print("\nTekan Enter Untuk Kembali...");
        scanner.nextLine();
    }

    private static String centerText(String text, int width) {
        if (text == null) text = "";
        if (text.length() >= width) {
            return text.substring(0, width); // Potong jika lebih panjang
        }
        int paddingSize = width - text.length();
        int padStart = paddingSize / 2;
        int padEnd = paddingSize - padStart;
        return " ".repeat(padStart) + text + " ".repeat(padEnd);
    }

    public static void showErrorMessage(String message) {
        redColor();
        System.out.println("=========================================================================");
        System.out.println("ERROR: " + message);
        System.out.println("=========================================================================");
        blueColor();
        delay(1500);
    }

    public static void showSuccessMessage(String message) {
        greenColor();
        String border = "=".repeat(LINE_WIDTH);
        System.out.println(border);
        System.out.println("=" + centerText(message, LINE_WIDTH - 2) + "=");
        System.out.println(border);
        resetColor();
        delay(1000);
    }

    public static void showInfoMessage(String title, String messageContent) {
        blueColor();
        String border = "=".repeat(LINE_WIDTH);
        String titleLine = "=" + centerText(title.toUpperCase(), LINE_WIDTH - 2) + "=";

        System.out.println(border);
        System.out.println(titleLine);
        System.out.println(border);

        if (messageContent != null && !messageContent.isEmpty()) {
            System.out.println("  " + messageContent);
            System.out.println("-".repeat(LINE_WIDTH));
        }
        resetColor();
    }

    public static String getInputString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public static int getInputInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                showErrorMessage("Input tidak valid. Harap masukkan angka.");
            }
        }
    }

    public static LocalDateTime getInputDateTime(String prompt) {
        while (true) {
            System.out.print(prompt + " (format dd/MM/yyyy HH:mm:ss): ");
            String input = scanner.nextLine();
            try {
                return LocalDateTime.parse(input, dateTimeFormatter);
            } catch (DateTimeParseException e) {
                showErrorMessage("Format tanggal dan waktu tidak valid. Gunakan format dd/MM/yyyy HH:mm:ss.");
            }
        }
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

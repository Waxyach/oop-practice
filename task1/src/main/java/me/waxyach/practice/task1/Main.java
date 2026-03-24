package me.waxyach.practice.task1;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {

    private static final Scanner SCANNER = new Scanner(System.in, StandardCharsets.UTF_8);

    private final Calculation calculation = new Calculation();

    public static void main(String[] args) {
        new Main().run();
    }

    public void run() {
        while (true) {
            printMenu();
            String input = SCANNER.nextLine().trim().toLowerCase();

            if (input.isEmpty()) continue;

            if (!handleCommand(input.charAt(0))) {
                break;
            }
        }
    }

    /**
     * Обробка команди користувача.
     * @return true, якщо програма має продовжувати роботу.
     */
    private boolean handleCommand(char commandChar) {
        switch (commandChar) {
            case 'v' -> viewCurrentState();
            case 'g' -> generateRandomData();
            case 's' -> saveState();
            case 'r' -> restoreState();
            case 'q' -> {
                System.out.println("Exiting program.");
                return false;
            }
            default -> System.out.println("Error: Unknown command.");
        }
        return true;
    }

    private void printMenu() {
        System.out.println("\n--- MENU ---");
        System.out.print("[V]iew, [G]enerate, [S]ave, [R]estore, [Q]uit: ");
    }

    private void viewCurrentState() {
        System.out.println("Current data in memory:");
        calculation.show();
    }

    private void generateRandomData() {
        System.out.println("Generating new parameters...");
        double voltage = Math.random() * 220 + 10;

        calculation.init(
                voltage,
                randomResist(),
                randomResist(),
                randomResist(),
                randomResist()
        );

        calculation.show();
    }

    private void saveState() {
        try {
            calculation.save();
            System.out.println("Data successfully serialized to file.");
        } catch (IOException e) {
            System.err.println("Critical save error: " + e.getMessage());
        }
    }

    private void restoreState() {
        System.out.println("Attempting to restore data...");
        try {
            calculation.restore();
            System.out.println("Deserialization successful.");
            calculation.show();
        } catch (Exception e) {
            System.err.println("Failed to restore object: " + e.getMessage());
        }
    }

    private double randomResist() {
        return Math.random() * 50 + 1;
    }
}
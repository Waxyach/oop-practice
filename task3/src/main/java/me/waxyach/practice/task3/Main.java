package me.waxyach.practice.task3;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import me.waxyach.practice.task2.View;
import me.waxyach.practice.task2.Viewable;

public class Main {

    private static final Scanner SCANNER = new Scanner(System.in, StandardCharsets.UTF_8);

    private final Viewable factory = ViewTable::new;
    private final View view = factory.getView();

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

    private boolean handleCommand(char command) {
        try {
            switch (command) {
                case 'v' -> view.viewShow();
                case 'g' -> {
                    System.out.println("Generating collection data...");
                    view.viewInit();
                    view.viewShow();
                }
                case 's' -> {
                    view.viewSave();
                    System.out.println("Collection successfully saved to file.");
                }
                case 'r' -> {
                    System.out.println("Restoring collection from file...");
                    view.viewRestore();
                    view.viewShow();
                }
                case 'q' -> {
                    System.out.println("Exiting program.");
                    return false;
                }
                default -> System.out.println("Error: Unknown command.");
            }
        } catch (Exception e) {
            System.err.println("Operation failed: " + e.getMessage());
        }
        return true;
    }

    private void printMenu() {
        System.out.println("\n--- COLLECTION MENU ---");
        System.out.print("[V]iew, [G]enerate, [S]ave, [R]estore, [Q]uit: ");
    }
}

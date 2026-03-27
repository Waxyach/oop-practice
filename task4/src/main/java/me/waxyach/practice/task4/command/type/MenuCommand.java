package me.waxyach.practice.task4.command.type;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import me.waxyach.practice.task4.command.Command;
import me.waxyach.practice.task4.command.ConsoleCommand;

/** Контейнер для консольних команд. */
public class MenuCommand implements Command {

    private static final Scanner SCANNER = new Scanner(System.in);

    private final List<ConsoleCommand> commands = new ArrayList<>();

    @Override
    public void execute() {
        while (true) {
            System.out.println("\n--- MAIN MENU ---");
            commands.forEach(c -> System.out.println("[" + c.getKey() + "]" + c));
            System.out.print("[Q]uit: ");

            String input = SCANNER.nextLine().trim().toLowerCase();
            if (input.equals("q")) break;
            if (input.isEmpty()) continue;

            boolean found = false;
            for (ConsoleCommand command : commands) {
                if (command.getKey() == input.charAt(0)) {
                    command.execute();
                    found = true;
                    break;
                }
            }
            if (!found) System.out.println("Wrong command.");
        }
    }

    public void add(ConsoleCommand cmd) {
        commands.add(cmd);
    }
}

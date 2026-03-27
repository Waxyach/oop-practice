package me.waxyach.practice.task4.command.type;

import java.util.ArrayList;
import java.util.List;

import me.waxyach.practice.task4.command.Command;

/**
 * Дозволяє виконувати та скасовувати групу команд як одну дію.
 */
public class MacroCommand implements Command {

    private final List<Command> commands = new ArrayList<>();

    @Override
    public void execute() {
        commands.forEach(Command::execute);
    }

    @Override
    public void undo() {
        for (int i = commands.size() - 1; i >= 0; i--) {
            commands.get(i).undo();
        }
    }

    public void add(Command cmd) {
        commands.add(cmd);
    }
}

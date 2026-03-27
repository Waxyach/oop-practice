package me.waxyach.practice.task4;

import java.io.IOException;
import java.util.Stack;

import lombok.Getter;

import me.waxyach.practice.task2.View;
import me.waxyach.practice.task2.ViewResult;
import me.waxyach.practice.task3.ViewableTable;
import me.waxyach.practice.task4.command.*;
import me.waxyach.practice.task4.command.type.ChangeItemCommand;
import me.waxyach.practice.task4.command.type.MacroCommand;
import me.waxyach.practice.task4.command.type.MenuCommand;

/** Головний клас додатка. */
public class Application {

    @Getter
    private static final Application instance = new Application();

    private final View view = new ViewableTable().getView();

    private final MenuCommand menu = new MenuCommand();

    private final Stack<Command> history = new Stack<>();

    private Application() {
        registerCommands();
    }

    /** Реєстрація команд. */
    private void registerCommands() {
        menu.add(viewCommand());
        menu.add(generateCommand());
        menu.add(saveCommand());
        menu.add(restoreCommand());
        menu.add(changeCommand());
        menu.add(undoCommand());
    }

    /** Формує меню та запускає обробку команд користувача. */
    public void run() {
        try {
            menu.execute();
        } catch (UnsupportedOperationException e) {
            System.err.println(e.getMessage());
        }
    }

    private ConsoleCommand viewCommand() {
        return new ConsoleCommand() {
            @Override
            public char getKey() {
                return 'v';
            }

            @Override
            public void execute() {
                System.out.println("View current.");
                view.viewShow();
            }

            @Override
            public String toString() {
                return "iew";
            }
        };
    }

    private ConsoleCommand generateCommand() {
        return new ConsoleCommand() {
            @Override
            public char getKey() {
                return 'g';
            }

            @Override
            public void execute() {
                System.out.println("Random generation.");
                view.viewInit();
                view.viewShow();
            }

            @Override
            public String toString() {
                return "enerate";
            }
        };
    }

    private ConsoleCommand saveCommand() {
        return new ConsoleCommand() {
            @Override
            public char getKey() {
                return 's';
            }

            @Override
            public void execute() {
                System.out.println("Save current.");
                try {
                    view.viewSave();
                    System.out.println("Data successfully saved.");
                } catch (IOException e) {
                    System.err.println("Serialization error: " + e.getMessage());
                }
                view.viewShow();
            }

            @Override
            public String toString() {
                return "ave";
            }
        };
    }

    private ConsoleCommand restoreCommand() {
        return new ConsoleCommand() {
            @Override
            public char getKey() {
                return 'r';
            }

            @Override
            public void execute() {
                System.out.println("Restore last saved.");
                try {
                    view.viewRestore();
                    System.out.println("Data successfully restored.");
                } catch (Exception e) {
                    System.err.println("Restore error: " + e.getMessage());
                }
                view.viewShow();
            }

            @Override
            public String toString() {
                return "estore";
            }
        };
    }

    private ConsoleCommand changeCommand() {
        return new ConsoleCommand() {
            @Override
            public char getKey() {
                return 'c';
            }

            @Override
            public void execute() {
                double factor = Math.random() * 2 + 0.5;
                System.out.printf("Scaling voltage by: %.2f\n", factor);

                MacroCommand batchChange = new MacroCommand();

                ((ViewResult) view).getDataList().forEach(item -> {
                    ChangeItemCommand c = new ChangeItemCommand();
                    c.setItem(item);
                    c.setScaleFactor(factor);

                    c.execute();
                    batchChange.add(c);
                });

                history.push(batchChange);

                view.viewShow();
            }

            @Override
            public String toString() {
                return "hange (Scale All)";
            }
        };
    }

    private ConsoleCommand undoCommand() {
        return new ConsoleCommand() {
            @Override
            public char getKey() {
                return 'u';
            }

            @Override
            public void execute() {
                if (!history.isEmpty()) {
                    System.out.println("Undoing last batch operation...");
                    history.pop().undo();
                    view.viewShow();
                } else {
                    System.out.println("Nothing to undo.");
                }
            }

            @Override
            public String toString() {
                return "ndo last action";
            }
        };
    }
}

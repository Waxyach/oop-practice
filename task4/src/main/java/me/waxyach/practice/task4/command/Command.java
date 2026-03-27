package me.waxyach.practice.task4.command;

/** * Інтерфейс команди. */
public interface Command {

    /** Виконання команди. */
    void execute();

    /**
     * Відміняє дію команди.
     * @throws UnsupportedOperationException якщо команда не підтримує відміну
     */
    default void undo() {
        throw new UnsupportedOperationException("Cannot undo this operation.");
    }
}

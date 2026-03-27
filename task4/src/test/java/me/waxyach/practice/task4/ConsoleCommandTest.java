package me.waxyach.practice.task4;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import me.waxyach.practice.task4.command.ConsoleCommand;

public class ConsoleCommandTest {

    /**
     * Перевірка інтерфейсу консольної команди.
     */
    @Test
    void testConsoleCommand() {
        ConsoleCommand cmd = new ConsoleCommand() {
            @Override
            public char getKey() {
                return 'z';
            }

            @Override
            public void execute() {}

            @Override
            public String toString() {
                return "test";
            }
        };

        assertEquals('z', cmd.getKey());
        assertEquals("test", cmd.toString());
    }
}

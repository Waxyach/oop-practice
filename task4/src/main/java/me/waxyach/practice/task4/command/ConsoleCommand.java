package me.waxyach.practice.task4.command;

/** Інтерфейс консольної команди з гарячою клавішею. */
public interface ConsoleCommand extends Command {

    /** Повертає символ гарячої клавіші. */
    char getKey();
}

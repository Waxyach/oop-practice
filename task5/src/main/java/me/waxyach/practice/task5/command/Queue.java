package me.waxyach.practice.task5.command;

import me.waxyach.practice.task4.command.Command;

/** * Інтерфейс для керування чергою завдань. */
public interface Queue {

    /** Додає нову задачу в чергу. */
    void put(Command cmd);

    /** Вилучає задачу з черги. */
    Command take();
}

package me.waxyach.practice.task5.command;

import java.util.Vector;

import me.waxyach.practice.task4.command.Command;

/** * Створює обробник потоку для виконання об'єктів Command. */
public class CommandQueue implements Queue {

    private final Vector<Command> tasks = new Vector<>();

    private boolean waiting = false;
    private boolean shutdown = false;

    public CommandQueue() {
        new Thread(new Worker()).start();
    }

    @Override
    public synchronized void put(Command cmd) {
        tasks.add(cmd);
        if (waiting) notifyAll();
    }

    @Override
    public synchronized Command take() {
        while (tasks.isEmpty()) {
            try {
                waiting = true;
                wait();
            } catch (InterruptedException e) {
                waiting = false;
            }
        }
        return tasks.removeFirst();
    }

    public void shutdown() {
        shutdown = true;
    }

    private class Worker implements Runnable {

        public void run() {
            while (!shutdown) {
                Command r = take();
                r.execute();
            }
        }
    }
}

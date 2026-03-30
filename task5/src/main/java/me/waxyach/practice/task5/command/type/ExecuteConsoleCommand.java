package me.waxyach.practice.task5.command.type;

import java.util.concurrent.TimeUnit;

import me.waxyach.practice.task2.View;
import me.waxyach.practice.task2.ViewResult;
import me.waxyach.practice.task4.command.ConsoleCommand;
import me.waxyach.practice.task5.command.CommandQueue;

public class ExecuteConsoleCommand implements ConsoleCommand {

    private final View view;

    public ExecuteConsoleCommand(View view) {
        this.view = view;
    }

    @Override
    public char getKey() {
        return 'e';
    }

    @Override
    public String toString() {
        return "'e'xecute threads";
    }

    @Override
    public void execute() {
        CommandQueue queue1 = new CommandQueue();
        CommandQueue queue2 = new CommandQueue();

        AvgCommand avg = new AvgCommand((ViewResult) view);
        MaxCommand max = new MaxCommand((ViewResult) view);
        MinMaxCommand minMax = new MinMaxCommand((ViewResult) view);

        System.out.println("Executing parallel tasks...");

        queue1.put(minMax);
        queue2.put(max);
        queue2.put(avg);

        try {
            while (avg.isRunning() || max.running() || minMax.isRunning()) {
                TimeUnit.MILLISECONDS.sleep(100);
            }

            queue1.shutdown();
            queue2.shutdown();
            System.out.println("All threads finished.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

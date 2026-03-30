package me.waxyach.practice.task5.command.type;

import java.util.concurrent.TimeUnit;

import lombok.Getter;

import me.waxyach.practice.task2.ViewResult;
import me.waxyach.practice.task4.command.Command;

/**
 * Задача обчислення середнього значення.
 */
public class AvgCommand implements Command {

    private final ViewResult viewResult;

    @Getter
    private double result = 0.0;

    private int progress = 0;

    public AvgCommand(ViewResult viewResult) {
        this.viewResult = viewResult;
    }

    public boolean isRunning() {
        return progress < 100;
    }

    @Override
    public void execute() {
        progress = 0;
        System.out.println("Average task executed...");
        double sum = 0;
        var items = viewResult.getDataList();
        int size = items.size();

        for (int i = 0; i < size; i++) {
            sum += items.get(i).getVoltage();
            progress = (i + 1) * 100 / size;

            try {
                TimeUnit.MILLISECONDS.sleep(2000 / size);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        result = sum / size;
        System.out.printf("Average done. Result: %.2f\n", result);
        progress = 100;
    }
}

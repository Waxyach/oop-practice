package me.waxyach.practice.task5.command.type;

import java.util.concurrent.TimeUnit;

import me.waxyach.practice.task2.ViewResult;
import me.waxyach.practice.task4.command.Command;

/**
 * Задача знаходження максимального значення.
 */
public class MaxCommand implements Command {

    private final ViewResult viewResult;

    private int progress = 0;

    public MaxCommand(ViewResult viewResult) {
        this.viewResult = viewResult;
    }

    public boolean running() {
        return progress < 100;
    }

    @Override
    public void execute() {
        progress = 0;
        System.out.println("Max task executed...");
        var items = viewResult.getDataList();
        int size = items.size();
        int resultId = 0;

        for (int i = 1; i < size; i++) {
            if (items.get(i).getVoltage() > items.get(resultId).getVoltage()) {
                resultId = i;
            }
            progress = (i + 1) * 100 / size;
            try {
                TimeUnit.MILLISECONDS.sleep(3000 / size);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Max done. Max voltage: " + items.get(resultId).getVoltage());
        progress = 100;
    }
}

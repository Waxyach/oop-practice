package me.waxyach.practice.task5.command.type;

import java.util.concurrent.TimeUnit;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import me.waxyach.practice.task1.CircuitData;
import me.waxyach.practice.task2.ViewResult;
import me.waxyach.practice.task4.command.Command;

/**
 * Задача для пошуку мінімального позитивного та максимального негативного значень.
 */
@RequiredArgsConstructor
public class MinMaxCommand implements Command {

    private final ViewResult viewResult;

    @Getter
    private int resultMin = -1;

    @Getter
    private int resultMax = -1;

    private int progress = 0;

    public boolean isRunning() {
        return progress < 100;
    }

    @Override
    public void execute() {
        progress = 0;
        System.out.println("MinMax task executed...");
        var items = viewResult.getDataList();
        int size = items.size();

        for (int idx = 0; idx < size; idx++) {
            CircuitData item = items.get(idx);
            double val = item.getVoltage();

            if (val > 0) {
                if (resultMin == -1 || val < items.get(resultMin).getVoltage()) {
                    resultMin = idx;
                }
            } else if (val < 0) {
                if (resultMax == -1 || val > items.get(resultMax).getVoltage()) {
                    resultMax = idx;
                }
            }

            progress = (idx + 1) * 100 / size;
            try {
                TimeUnit.MILLISECONDS.sleep(5000 / size);
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        }

        printResults();
        progress = 100;
    }

    private void printResults() {
        System.out.print("MinMax done. ");
        if (resultMin != -1) {
            System.out.print(
                    "Min positive: " + viewResult.getDataList().get(resultMin).getVoltage() + " ");
        } else {
            System.out.print("Min positive not found. ");
        }

        if (resultMax != -1) {
            System.out.println(
                    "Max negative: " + viewResult.getDataList().get(resultMax).getVoltage());
        } else {
            System.out.println("Max negative not found.");
        }
    }
}

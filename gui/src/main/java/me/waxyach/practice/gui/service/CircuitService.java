package me.waxyach.practice.gui.service;

import java.util.List;
import java.util.stream.Collectors;

import me.waxyach.practice.task1.CircuitData;

/**
 * Клас для обробки та маніпулювання даними електричних ланцюгів {@link CircuitData}.
 */
public class CircuitService {

    /**
     * Створює глибоку копію наданого списку об'єктів {@link CircuitData}.
     *
     * @param original оригінальний список даних ланцюга, який потрібно скопіювати.
     * @return новий список, що містить глибокі копії об'єктів {@link CircuitData}.
     * Повертає порожній список, якщо оригінал порожній.
     */
    public List<CircuitData> cloneList(List<CircuitData> original) {
        return original.stream()
                .map(data -> {
                    CircuitData copy = new CircuitData();
                    copy.setVoltage(data.getVoltage());

                    double[] res = data.getResistances();
                    if (res != null) {
                        copy.setResistances(res.clone());
                    } else {
                        copy.setResistances(new double[0]);
                    }

                    String[] bin = data.getBinaryCurrents();
                    if (bin != null) {
                        copy.setBinaryCurrents(bin.clone());
                    } else {
                        copy.setBinaryCurrents(new String[0]);
                    }

                    return copy;
                })
                .collect(Collectors.toList());
    }

    /**
     * Змінює напругу для кожного елемента в наданому списку на вказану величину.
     *
     * @param data список даних ланцюга, який потрібно оновити.
     * @param delta величина зміни напруги (може бути додатною або від'ємною).
     */
    public void applyVoltage(List<CircuitData> data, double delta) {
        data.forEach(item -> {
            // Оновлення напруги
            double newU = item.getVoltage() + delta;
            item.setVoltage(newU);

            // Автоматичний та безпечний перерахунок струмів
            updateBinaryCurrents(item);
        });
    }

    /**
     * Метод для перерахунку та оновлення двійкових представлень струмів
     * для одного об'єкта {@link CircuitData} на основі його поточної напруги та опорів.
     *
     * @param data об'єкт даних ланцюга, який потрібно оновити.
     */
    private void updateBinaryCurrents(CircuitData data) {
        double u = data.getVoltage();
        double[] r = data.getResistances();

        if (r == null || r.length == 0) {
            data.setBinaryCurrents(new String[0]);
            return;
        }

        String[] binary = new String[r.length];
        for (int i = 0; i < r.length; i++) {
            binary[i] = Integer.toBinaryString((int) (u / r[i]));
        }
        data.setBinaryCurrents(binary);
    }
}

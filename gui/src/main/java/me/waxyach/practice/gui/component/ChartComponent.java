package me.waxyach.practice.gui.component;

import java.util.Comparator;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import me.waxyach.practice.task1.CircuitData;

/**
 * Кастомний компонент лінійного графіка ({@link LineChart}), призначений для візуалізації
 * залежності струму (I) від опору (R) на основі даних {@link CircuitData}.
 * <p>
 * Компонент автоматично оновлює своє відображення при будь-яких змінах у наданому
 * оборотному списку даних ({@link ObservableList}) завдяки зареєстрованому слухачу.
 *
 * @see CircuitData
 */
public class ChartComponent extends LineChart<Number, Number> {

    public ChartComponent(ObservableList<CircuitData> data) {
        super(createAxis("Опір (Ω)", false), createAxis("Струм (I)", true));

        setTitle("Залежність струму від опору");
        setAnimated(true);
        setCreateSymbols(true);

        data.addListener((ListChangeListener<CircuitData>) c -> updateChart(data));

        if (!data.isEmpty()) {
            updateChart(data);
        }
    }

    /**
     * Допоміжний статичний метод для створення та налаштування осі чисел ({@link NumberAxis}).
     *
     * @param label назва осі (мітка).
     * @param forceZero чи обов'язково включати нуль у діапазон відображення осі.
     * @return налаштований об'єкт {@link NumberAxis}.
     */
    private static NumberAxis createAxis(String label, boolean forceZero) {
        NumberAxis axis = new NumberAxis();
        axis.setLabel(label);
        axis.setAutoRanging(true);
        axis.setForceZeroInRange(forceZero);
        return axis;
    }

    /**
     * Метод для повного перемалювання графіка на основі поточного стану даних.
     * <p>
     * Метод очищає поточні дані графіка, а потім ітерується чотири рази (для кожного резистора R1-R4).
     *
     * @param observableData поточний список даних ланцюга.
     */
    private void updateChart(ObservableList<CircuitData> observableData) {
        getData().clear();

        for (int i = 0; i < 4; i++) {
            final int resIdx = i;

            var series = new XYChart.Series<Number, Number>();
            series.setName("Резистор R" + (resIdx + 1));

            var points = observableData.stream()
                    .filter(d -> d.getResistances() != null && d.getResistances().length > resIdx)
                    .map(d -> {
                        double r = d.getResistances()[resIdx];
                        double current = d.getVoltage() / r;
                        return new XYChart.Data<Number, Number>(r, current);
                    })
                    .sorted(Comparator.comparingDouble(d -> d.getXValue().doubleValue()))
                    .toList();

            series.getData().addAll(points);

            if (!series.getData().isEmpty()) {
                getData().add(series);
            }
        }
    }
}

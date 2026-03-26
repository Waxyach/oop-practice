package me.waxyach.practice.task1;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CircuitDataTest {

    /** Перевірка серіалізації та коректності відновлення даних */
    @Test
    void testRestore() {
        Calculation calculation = new Calculation();
        calculation.init(220.0, 5.0, 10.0, 15.0, 20.0);

        String[] expectedCurrents = Arrays.copyOf(calculation.getData().getBinaryCurrents(), 4);

        try {
            calculation.save();
            calculation.init(0, 1, 1, 1, 1);
            calculation.restore();
        } catch (Exception e) {
            Assertions.fail("Виняток під час серіалізації/десеріалізації: " + e.getMessage());
        }

        // Перевіряємо, що результати відновилися правильно
        Assertions.assertArrayEquals(expectedCurrents, calculation.getData().getBinaryCurrents());

        // Перевіряємо особливість transient: масив resistances не серіалізується і повинен стати null
        Assertions.assertNull(
                calculation.getData().getResistances(),
                "Поле resistances має бути null після десеріалізації (transient)");
    }
}

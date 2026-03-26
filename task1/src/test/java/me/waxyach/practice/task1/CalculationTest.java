package me.waxyach.practice.task1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CalculationTest {

    /** Перевірка основної функціональності розрахунків */
    @Test
    void testCalculation() {
        Calculation calculation = new Calculation();

        calculation.init(100.0, 10.0, 20.0, 25.0, 50.0);

        String[] expected = {"1010", "101", "100", "10"};
        String[] actual = calculation.getData().getBinaryCurrents();

        Assertions.assertArrayEquals(expected, actual, "Розрахунок струму або двійкового представлення невірний");
    }
}

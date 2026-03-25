package me.waxyach.practice.task2;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import me.waxyach.practice.task1.CircuitData;

class ViewResultTest {

    /**
     * Перевірка основної функціональності: ініціалізація та розрахунок.
     */
    @Test
    void testCalculation() {
        ViewResult view = new ViewResult();
        view.viewInit();

        List<CircuitData> list = view.getDataList();

        assertFalse(list.isEmpty(), "Колекція не повинна бути порожньою");
        assertEquals(5, list.size(), "Розмір колекції має бути 5 за замовчуванням");

        CircuitData sample = list.getFirst();
        double voltage = sample.getVoltage();
        double resistance = sample.getResistances()[0];
        String expectedBinary = Integer.toBinaryString((int) (voltage / resistance));

        assertEquals(expectedBinary, sample.getBinaryCurrents()[0], "Двійкове значення струму розраховано невірно");
    }

    /**
     * Перевірка серіалізації: збереження та відновлення всієї колекції.
     */
    @Test
    void testRestore() {
        ViewResult view1 = new ViewResult();
        view1.viewInit();

        List<CircuitData> originalData = List.copyOf(view1.getDataList());

        try {
            view1.viewSave();

            ViewResult view2 = new ViewResult();
            view2.viewRestore();

            List<CircuitData> restoredData = view2.getDataList();

            assertEquals(originalData.size(), restoredData.size(), "Кількість відновлених елементів не збігається");

            assertEquals(originalData, restoredData, "Вміст відновленої колекції відрізняється від оригіналу");

        } catch (IOException e) {
            fail("Помилка при збереженні: " + e.getMessage());
        } catch (Exception e) {
            fail("Помилка при відновленні: " + e.getMessage());
        }
    }
}

package me.waxyach.practice.task4;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import me.waxyach.practice.task1.CircuitData;
import me.waxyach.practice.task4.command.type.ChangeItemCommand;
import me.waxyach.practice.task4.command.type.MacroCommand;

public class MacroCommandTest {

    /**
     * Перевірка роботи макрокоманди для всієї колекції.
     */
    @Test
    void testMacroUndo() {
        List<CircuitData> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            CircuitData data = new CircuitData();
            data.setVoltage(10.0);
            data.setResistances(new double[] {1.0, 1.0, 1.0, 1.0});
            list.add(data);
        }

        MacroCommand macro = new MacroCommand();
        for (CircuitData item : list) {
            ChangeItemCommand c = new ChangeItemCommand();
            c.setItem(item);
            c.setScaleFactor(5.0);
            c.execute();
            macro.add(c);
        }

        for (CircuitData item : list) {
            assertEquals(50.0, item.getVoltage(), 0.001);
        }

        macro.undo();

        for (CircuitData item : list) {
            assertEquals(10.0, item.getVoltage(), 0.001, "Всі елементи мають повернутися до 10.0");
        }
    }
}

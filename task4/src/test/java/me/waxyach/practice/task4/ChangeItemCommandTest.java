package me.waxyach.practice.task4;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import me.waxyach.practice.task1.CircuitData;
import me.waxyach.practice.task4.command.type.ChangeItemCommand;

public class ChangeItemCommandTest {

    /**
     * Перевірка методу execute() та undo() для однієї команди.
     */
    @Test
    void testExecuteAndUndo() {
        CircuitData item = new CircuitData();
        double initialVoltage = 100.0;
        item.setVoltage(initialVoltage);
        item.setResistances(new double[] {10.0, 10.0, 10.0, 10.0});

        ChangeItemCommand cmd = new ChangeItemCommand();
        cmd.setItem(item);
        cmd.setScaleFactor(2.0);

        cmd.execute();
        assertEquals(200.0, item.getVoltage(), 0.001, "Напруга має бути 200.0");
        assertEquals(Integer.toBinaryString(20), item.getBinaryCurrents()[0], "Струм має бути 20 (двійкове)");

        cmd.undo();
        assertEquals(initialVoltage, item.getVoltage(), 0.001, "Напруга має повернутися до 100.0");
    }
}

package me.waxyach.practice.task5;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import me.waxyach.practice.task1.CircuitData;
import me.waxyach.practice.task2.ViewResult;
import me.waxyach.practice.task5.command.type.MaxCommand;

class MaxCommandTest {

    @Test
    void testMaxFinding() {
        ViewResult view = new ViewResult(10);
        view.viewInit();
        MaxCommand maxCommand = new MaxCommand(view);

        maxCommand.execute();

        double maxInList = view.getDataList().stream()
                .mapToDouble(CircuitData::getVoltage)
                .max()
                .orElse(-1);

        assertFalse(maxCommand.running());
        assertTrue(maxInList > 0);
    }
}

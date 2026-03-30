package me.waxyach.practice.task5;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import me.waxyach.practice.task1.CircuitData;
import me.waxyach.practice.task2.ViewResult;
import me.waxyach.practice.task5.command.type.AvgCommand;

class AvgCommandTest {

    private ViewResult view;

    private AvgCommand avgCommand;

    @BeforeEach
    void setUp() {
        view = new ViewResult(3);
        view.viewInit();
        avgCommand = new AvgCommand(view);
    }

    @Test
    void testAvgCalculation() {
        avgCommand.execute();

        double expected = view.getDataList().stream()
                .mapToDouble(CircuitData::getVoltage)
                .average()
                .orElse(0);

        assertEquals(expected, avgCommand.getResult(), 1e-10, "Середнє значення розраховано невірно");
    }
}

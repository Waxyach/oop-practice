package me.waxyach.practice.task5;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import me.waxyach.practice.task1.CircuitData;
import me.waxyach.practice.task2.ViewResult;
import me.waxyach.practice.task5.command.type.MinMaxCommand;

class MinMaxCommandTest {

    @Test
    void testMinMaxLogic() {
        ViewResult view = new ViewResult(0);

        CircuitData d1 = new CircuitData();
        d1.setVoltage(-10.0);
        CircuitData d2 = new CircuitData();
        d2.setVoltage(-5.0);
        CircuitData d3 = new CircuitData();
        d3.setVoltage(2.0);
        CircuitData d4 = new CircuitData();
        d4.setVoltage(20.0);

        view.getDataList().addAll(java.util.List.of(d1, d2, d3, d4));

        MinMaxCommand cmd = new MinMaxCommand(view);
        cmd.execute();

        assertTrue(cmd.getResultMin() != -1);
        assertTrue(cmd.getResultMax() != -1);
    }
}

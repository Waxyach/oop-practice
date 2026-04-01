package me.waxyach.practice.gui;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import me.waxyach.practice.gui.service.CircuitService;
import me.waxyach.practice.task1.CircuitData;

class CircuitServiceTest {

    private CircuitService service;
    private List<CircuitData> testList;

    @BeforeEach
    void setUp() {
        service = new CircuitService();
        testList = new ArrayList<>();

        CircuitData data = new CircuitData();
        data.setVoltage(10.0);
        data.setResistances(new double[] {5.0, 10.0});
        testList.add(data);
    }

    @Test
    void testApplyVoltageUpdatesBinaryCurrents() {
        service.applyVoltage(testList, 10.0);

        assertEquals(20.0, testList.getFirst().getVoltage());
        assertEquals("100", testList.getFirst().getBinaryCurrents()[0]);
    }

    @Test
    void testDeepClone() {
        List<CircuitData> clonedList = service.cloneList(testList);

        assertNotSame(testList.getFirst(), clonedList.getFirst());
        assertEquals(testList.getFirst().getVoltage(), clonedList.getFirst().getVoltage());
    }
}

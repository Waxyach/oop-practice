package me.waxyach.practice.task3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ViewTableTest {

    /**
     * Перевірка ініціалізації.
     */
    @Test
    void testTableWidth() {
        ViewTable table = new ViewTable(50);
        assertEquals(50, table.getWidth());

        table.init(30);
        assertEquals(30, table.getWidth());
    }
}
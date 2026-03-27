package me.waxyach.practice.task4;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class ApplicationTest {

    /**
     * Перевірка реалізації шаблону Singleton.
     */
    @Test
    void testSingleton() {
        Application instance1 = Application.getInstance();
        Application instance2 = Application.getInstance();

        assertSame(instance1, instance2, "Application має бути єдиним екземпляром");
    }
}

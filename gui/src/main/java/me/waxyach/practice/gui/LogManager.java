package me.waxyach.practice.gui;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.TextArea;

import me.waxyach.practice.task1.CircuitData;

/**
 * Менеджер логування, що забезпечує виведення системних повідомлень, помилок
 * та результатів аналізу в компонент {@link TextArea} користувацького інтерфейсу.
 */
public class LogManager {

    private final TextArea logArea;

    private final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

    public LogManager(TextArea logArea) {
        this.logArea = logArea;
    }

    /**
     * Додає інформаційне повідомлення до логу з префіксом [INFO].
     *
     * @param message текст повідомлення.
     */
    public void info(String message) {
        append("INFO", message);
    }

    /**
     * Додає повідомлення про помилку до логу з префіксом [ERROR].
     *
     * @param message текст помилки.
     */
    public void error(String message) {
        append("ERROR", message);
    }

    /**
     * Додає статистичне повідомлення (результат розрахунків) з префіксом [STAT].
     * Форматує числове значення до двох знаків після коми.
     *
     * @param title назва статистичного показника (наприклад, "Середня напруга").
     * @param value числове значення показника.
     */
    public void stat(String title, double value) {
        append("STAT", String.format("%s: %.2f", title, value));
    }

    /**
     * Логує деталі об'єкта {@link CircuitData}, використовуючи механізм рефлексії.
     *
     * @param data об'єкт ланцюга, дані якого потрібно залогувати.
     */
    public void logCircuit(CircuitData data) {
        try {
            // Отримання доступу до приватного поля через рефлексію
            var field = data.getClass().getDeclaredField("voltage");
            field.setAccessible(true);

            double v = field.getDouble(data);
            append("DATA", String.format("Сгенеровано новий об'єкт: U = %.2fV", v));
        } catch (Exception e) {
            append("ERROR", "Не вдалося прочитати дані через Reflection");
        }
    }

    /**
     * Внутрішній метод для форматування та запису повідомлення в текстову область.
     * <p>
     * Кожне повідомлення формується у вигляді: {@code [HH:mm:ss] [LEVEL] Message}.
     * Після додавання тексту метод автоматично прокручує {@link TextArea} до кінця.
     * </p>
     *
     * @param level рівень логування (INFO, ERROR, STAT, DATA).
     * @param message текст повідомлення.
     */
    private void append(String level, String message) {
        String timestamp = LocalTime.now().format(timeFormat);

        logArea.appendText(String.format("[%s] [%s] %s\n", timestamp, level, message));

        logArea.setScrollTop(Double.MAX_VALUE);
    }
}

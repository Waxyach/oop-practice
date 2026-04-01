package me.waxyach.practice.gui.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Допоміжний утилітний клас для роботи з Java Reflection API та форматуванням даних.
 */
public class ReflectionHelper {

    /**
     * Конвертує значення об'єкта у форматований рядок, придатний для відображення в UI.
     *
     * @param value об'єкт, значення якого потрібно перетворити на рядок.
     * @return форматований рядок або порожній рядок, якщо об'єкт дорівнює {@code null}.
     */
    public static String valueToString(Object value) {
        return switch (value) {
            case null -> "";

            case double[] doubles -> Arrays.stream(doubles)
                    .mapToObj(d -> String.format("%.2f", d))
                    .collect(Collectors.joining(", "));

            case String[] strings -> String.join(", ", strings);

            case Double v -> String.format("%.2f", v);

            default -> value.toString();
        };
    }

    /**
     * Динамічно оновлює значення вказаного поля об'єкта, конвертуючи вхідний рядок
     * у відповідний тип даних поля.
     *
     * @param obj об'єкт, поле якого потрібно оновити.
     * @param field об'єкт поля ({@link Field}), отриманий через рефлексію.
     * @param value нове значення у вигляді рядка (наприклад, введене користувачем у таблиці).
     * @throws Exception якщо виникає помилка доступу до поля або помилка парсингу рядка
     * (наприклад, {@link NumberFormatException}).
     */
    public static void updateField(Object obj, Field field, String value) throws Exception {
        field.setAccessible(true);

        if (field.getType() == double.class || field.getType() == Double.class) {
            field.set(obj, Double.parseDouble(value));
        } else if (field.getType() == double[].class) {
            double[] array = Arrays.stream(value.split(","))
                    .map(String::trim)
                    .mapToDouble(Double::parseDouble)
                    .toArray();

            field.set(obj, array);
        }
    }
}
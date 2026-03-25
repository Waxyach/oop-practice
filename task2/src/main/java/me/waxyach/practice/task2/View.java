package me.waxyach.practice.task2;

import java.io.IOException;

/**
 * Оголошує методи для відображення та керування даними результатів обчислень.
 */
public interface View {

    /**
     * Відображає об'єкт або колекцію об'єктів цілком (включаючи заголовок та підвал).
     */
    void viewShow();

    /**
     * Виконує ініціалізацію даних для обчислень.
     */
    void viewInit();

    /**
     * Зберігає поточні дані для їх подальшого відновлення.
     * @throws IOException помилка під час запису у файл.
     */
    void viewSave() throws IOException;

    /**
     * Відновлює раніше збережені дані з файлу.
     * @throws Exception помилка під час читання або десеріалізації даних.
     */
    void viewRestore() throws Exception;
}

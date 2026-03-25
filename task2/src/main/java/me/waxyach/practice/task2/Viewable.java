package me.waxyach.practice.task2;

/**
 * Оголошує метод, що "фабрикує" об'єкти.
 */
@FunctionalInterface
public interface Viewable {

    /**
     * Створює об'єкт, що реалізує інтерфейс {@link View}.
     * @return новий об'єкт типу View.
     */
    View getView();
}

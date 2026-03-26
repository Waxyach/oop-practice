package me.waxyach.practice.task3;

import me.waxyach.practice.task2.View;
import me.waxyach.practice.task2.Viewable;

/**
 * Оголошує метод, що фабрикує об'єкт ViewTable.
 */
public class ViewableTable implements Viewable {

    @Override
    public View getView() {
        return new ViewTable();
    }
}

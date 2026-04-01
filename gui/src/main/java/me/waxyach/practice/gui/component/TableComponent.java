package me.waxyach.practice.gui.component;

import java.lang.reflect.Field;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;

import me.waxyach.practice.gui.util.ReflectionHelper;
import me.waxyach.practice.task1.CircuitData;
import me.waxyach.practice.task1.Displayable;

/**
 * Кастомний компонент таблиці ({@link TableView}), призначений для відображення
 * та редагування списку об'єктів {@link CircuitData}.
 * <p>
 * Таблиця підтримує редагування осередків "на місці" за допомогою текстових полів.
 * Зміни, внесені користувачем, автоматично застосовуються до базових об'єктів даних
 * через {@link ReflectionHelper}.
 *
 * @see CircuitData
 * @see Displayable
 * @see ReflectionHelper
 */
public class TableComponent extends TableView<CircuitData> {

    public TableComponent(ObservableList<CircuitData> data) {
        super(data);
        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        generateColumns();
    }

    /**
     * Метод, який використовує рефлексію для динамічного створення
     * стовпців таблиці ({@link TableColumn}) на основі полів класу {@link CircuitData}.
     *
     * @throws SecurityException якщо доступ до полів класу обмежений політикою безпеки.
     */
    private void generateColumns() {
        Field[] fields = CircuitData.class.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Displayable.class)) {
                Displayable annotation = field.getAnnotation(Displayable.class);
                var col = new TableColumn<CircuitData, String>(annotation.columnName());

                col.setCellValueFactory(cell -> {
                    try {
                        field.setAccessible(true);
                        return new SimpleStringProperty(ReflectionHelper.valueToString(field.get(cell.getValue())));
                    } catch (Exception e) {
                        return new SimpleStringProperty("");
                    }
                });

                col.setCellFactory(TextFieldTableCell.forTableColumn());

                col.setOnEditCommit(event -> {
                    try {
                        ReflectionHelper.updateField(event.getRowValue(), field, event.getNewValue());
                        refresh();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                getColumns().add(col);
            }
        }
    }
}

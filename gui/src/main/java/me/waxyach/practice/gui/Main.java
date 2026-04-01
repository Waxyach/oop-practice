package me.waxyach.practice.gui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import me.waxyach.practice.gui.component.ChartComponent;
import me.waxyach.practice.gui.component.TableComponent;
import me.waxyach.practice.gui.service.CircuitService;
import me.waxyach.practice.task1.CircuitData;
import me.waxyach.practice.task2.ViewResult;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Головний клас додатка, що керує життєвим циклом програми та
 * користувацьким інтерфейсом.
 */
public class Main extends Application {

    /** Основний список даних, синхронізований з елементами інтерфейсу. */
    private final ObservableList<CircuitData> observableData = FXCollections.observableArrayList();

    private final ViewResult dataManager = new ViewResult();

    private final CircuitService service = new CircuitService();

    /** Стек для зберігання історії станів списку даних. */
    private final Stack<List<CircuitData>> undoStack = new Stack<>();

    private LogManager logger;

    private Button undoBtn;

    private Button restoreBtn;

    @Override
    public void start(Stage primaryStage) {
        TextArea logArea = new TextArea();
        logArea.setEditable(false);
        logger = new LogManager(logArea);

        TableComponent table = new TableComponent(observableData);
        ChartComponent chart = new ChartComponent(observableData);

        TabPane tabs = new TabPane();
        tabs.getTabs().addAll(
                createTab("Таблиця", table),
                createTab("Графік", chart),
                createTab("Лог", logArea)
        );

        BorderPane root = new BorderPane();
        root.setCenter(tabs);
        root.setBottom(createActionPanel());

        Scene scene = new Scene(root, 1050, 700);

        URL cssPath = getClass().getResource("/style.css");
        if (cssPath != null) {
            scene.getStylesheets().add(cssPath.toExternalForm());
        }

        URL iconPath = getClass().getResource("/icon.png");
        if (iconPath != null) {
            primaryStage.getIcons().add(new Image(iconPath.toExternalForm()));
        }

        primaryStage.setTitle("Circuit Analyzer");
        primaryStage.setScene(scene);
        primaryStage.show();

        updateFileButtonsState();
    }

    /**
     * Створює нижню панель інструментів із кнопками керування та аналізу.
     * Панель включає файлові операції, генерацію, статистичні кнопки (AVG, MAX, MIN)
     * та функціонал зміни напруги.
     *
     * @return налаштований об'єкт {@link HBox} (панель дій).
     */
    private HBox createActionPanel() {
        HBox bar = new HBox(12);
        bar.setPadding(new Insets(15));
        bar.setAlignment(Pos.CENTER);
        bar.getStyleClass().add("bottom-bar");

        Button saveBtn = new Button("Зберегти");
        restoreBtn = new Button("Відновити");
        undoBtn = new Button("Відмінити");
        undoBtn.setDisable(true);

        saveBtn.setOnAction(e -> handleSave());
        restoreBtn.setOnAction(e -> handleRestore());
        undoBtn.setOnAction(e -> handleUndo());

        Region s1 = new Region();
        HBox.setHgrow(s1, Priority.ALWAYS);

        Button genBtn = new Button("Згенерувати");
        genBtn.getStyleClass().add("button-primary");
        genBtn.setOnAction(e -> handleGenerate());

        Region s2 = new Region();
        HBox.setHgrow(s2, Priority.ALWAYS);

        Button avgBtn = new Button("AVG");
        Button maxBtn = new Button("MAX");
        Button minBtn = new Button("MIN");

        avgBtn.setOnAction(e -> logger.stat("Середня U", observableData.stream().mapToDouble(CircuitData::getVoltage).average().orElse(0)));
        maxBtn.setOnAction(e -> logger.stat("Макс. U", observableData.stream().mapToDouble(CircuitData::getVoltage).max().orElse(0)));
        minBtn.setOnAction(e -> logger.stat("Мін. U", observableData.stream().mapToDouble(CircuitData::getVoltage).min().orElse(0)));

        TextField voltageInput = new TextField("10.0");
        voltageInput.setPrefWidth(55);
        Button raiseBtn = new Button("Підняти U");
        raiseBtn.setOnAction(e -> handleVoltageChange(voltageInput.getText()));

        bar.getChildren().addAll(saveBtn, restoreBtn, undoBtn, s1, genBtn, s2, avgBtn, maxBtn, minBtn, new Separator(), voltageInput, raiseBtn);
        return bar;
    }

    /**
     * Обробляє команду генерації випадкових даних.
     * Перед генерацією зберігає поточний стан у стек відміни.
     */
    private void handleGenerate() {
        saveToUndoStack();

        dataManager.viewInit();
        observableData.setAll(dataManager.getDataList());

        logger.info("Згенеровано нові дані (" + observableData.size() + " записів).");
    }

    /**
     * Виконує групову зміну напруги для всіх об'єктів у списку.
     *
     * @param input рядкове значення величини зміни напруги (delta).
     */
    private void handleVoltageChange(String input) {
        try {
            double delta = Double.parseDouble(input);
            saveToUndoStack();

            service.applyVoltage(observableData, delta);
            observableData.setAll(new ArrayList<>(observableData));

            logger.info("Напругу змінено на " + delta + "V.");
        } catch (NumberFormatException e) {
            logger.error("Помилка: введіть числове значення напруги.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Зберігає поточний список даних у бінарний файл через {@link ViewResult}.
     */
    private void handleSave() {
        try {
            dataManager.viewSave();
            logger.info("Дані успішно збережено у файл.");
            updateFileButtonsState();
        } catch (Exception e) {
            logger.error("Помилка збереження у файл.");
        }
    }

    /**
     * Відновлює список даних із файлу.
     * Подія відновлення також зберігається в стеку Undo, дозволяючи скасувати завантаження.
     */
    private void handleRestore() {
        try {
            saveToUndoStack();
            dataManager.viewRestore();
            observableData.setAll(dataManager.getDataList());
            logger.info("Стан відновлено з файлу.");
        } catch (Exception e) {
            logger.error("Не вдалося завантажити дані.");
        }
    }

    /**
     * Скасовує останню зміну даних, повертаючи попередній стан зі стека.
     */
    private void handleUndo() {
        if (!undoStack.isEmpty()) {
            observableData.setAll(undoStack.pop());
            logger.info("Дію скасовано. Кроків в історії: " + undoStack.size());
            undoBtn.setDisable(undoStack.isEmpty());
        }
    }

    /**
     * Створює глибоку копію поточного списку даних та додає її до стека історії.
     */
    private void saveToUndoStack() {
        undoStack.push(service.cloneList(observableData));
        undoBtn.setDisable(false);
    }

    /**
     * Оновлює стан доступності кнопки відновлення залежно від наявності файлу на диску.
     */
    private void updateFileButtonsState() {
        File file = new File(ViewResult.FILE_NAME);
        restoreBtn.setDisable(!file.exists());
    }

    /**
     * Допоміжний метод для створення вкладки, яку неможливо закрити.
     *
     * @param title заголовок вкладки.
     * @param content вузол (Node), що буде розміщений всередині вкладки.
     * @return налаштований об'єкт {@link Tab}.
     */
    private Tab createTab(String title, Node content) {
        Tab tab = new Tab(title, content);
        tab.setClosable(false);
        return tab;
    }
}
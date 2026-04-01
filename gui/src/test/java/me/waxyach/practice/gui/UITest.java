package me.waxyach.practice.gui;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.LabeledMatchers;

import static org.testfx.api.FxAssert.verifyThat;

public class UITest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        new Main().start(stage);
    }

    @Test
    void testGenerateFlow() {
        verifyThat(".button-primary", LabeledMatchers.hasText("Згенерувати"));

        clickOn("Згенерувати");

        verifyThat(".text-area", (TextArea t) -> t.getText().contains("Згенеровано нові дані"));
    }

    @Test
    void testTabNavigation() {
        clickOn("Графік");
        verifyThat("Залежність струму від опору", NodeMatchers.isVisible());

        clickOn("Лог");
        verifyThat(".text-area", NodeMatchers.isVisible());

        clickOn("Таблиця");
        verifyThat(".table-view", NodeMatchers.isVisible());
    }

    @Test
    void testUndoLogic() {
        clickOn("Згенерувати");

        verifyThat("Відмінити", NodeMatchers.isEnabled());

        clickOn("Підняти U");
        clickOn("Відмінити");

        verifyThat(".text-area", (TextArea t) -> t.getText().contains("Дію скасовано"));
    }

    @Test
    void testInvalidVoltageInput() {
        TextField input = lookup(".text-field").queryAs(TextField.class);

        doubleClickOn(input).eraseText(10);
        write("текст");

        clickOn("Підняти U");

        verifyThat(".text-area", (TextArea t) -> t.getText().contains("ERROR")
                && t.getText().contains("числове значення"));
    }

    @Test
    void testAnalyticsButtons() {
        clickOn("Згенерувати");

        clickOn("AVG");
        verifyThat(".text-area", (TextArea t) -> t.getText().contains("STAT") && t.getText().contains("Середня U"));

        clickOn("MAX");
        verifyThat(".text-area", (TextArea t) -> t.getText().contains("Макс. U"));

        clickOn("MIN");
        verifyThat(".text-area", (TextArea t) -> t.getText().contains("Мін. U"));
    }

    @Test
    void testFileButtonsInitialState() {
        verifyThat("Зберегти", NodeMatchers.isEnabled());

        verifyThat("Відновити", NodeMatchers.isVisible());
    }
}
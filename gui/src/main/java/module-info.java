module gui {
    requires task1;
    requires task2;
    requires task3;
    requires task4;
    requires task5;
    requires javafx.controls;
    requires transitive javafx.graphics;

    opens me.waxyach.practice.gui to
            javafx.graphics;

    exports me.waxyach.practice.gui;
    exports me.waxyach.practice.gui.component;

    opens me.waxyach.practice.gui.component to
            javafx.graphics;

    exports me.waxyach.practice.gui.util;

    opens me.waxyach.practice.gui.util to
            javafx.graphics;
}

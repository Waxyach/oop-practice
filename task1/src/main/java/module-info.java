module task1 {
    requires static lombok;

    exports me.waxyach.practice.task1;

    opens me.waxyach.practice.task1 to
            javafx.base,
            javafx.graphics;
}

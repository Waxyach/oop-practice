module task4 {
    requires task1;
    requires task2;
    requires task3;
    requires static lombok;

    exports me.waxyach.practice.task4;
    exports me.waxyach.practice.task4.command;
    exports me.waxyach.practice.task4.command.type;
}

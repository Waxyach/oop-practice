module task5 {
    requires task1;
    requires task2;
    requires task3;
    requires task4;

    requires static lombok;

    exports me.waxyach.practice.task5;
    exports me.waxyach.practice.task5.command;
    exports me.waxyach.practice.task5.command.type;
}
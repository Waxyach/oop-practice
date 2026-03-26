package me.waxyach.practice.task3;

import java.util.Formatter;
import lombok.Getter;
import lombok.Setter;
import me.waxyach.practice.task1.CircuitData;
import me.waxyach.practice.task2.ViewResult;

/**
 * Клас для відображення результатів у вигляді форматованої текстової таблиці.
 */
public class ViewTable extends ViewResult {

    private static final int DEFAULT_WIDTH = 50;

    @Getter @Setter
    private int width;

    public ViewTable() {
        this(DEFAULT_WIDTH);
    }

    public ViewTable(int width) {
        this.width = width;
    }

    public ViewTable(int width, int size) {
        super(size);
        this.width = width;
    }

    public final void init(int width) {
        this.width = width;
        viewInit();
    }

    public final void init(int width, String message) {
        System.out.println(message);
        this.init(width);
    }

    @Override
    public void viewHeader() {
        outLineLn();
        Formatter fmt = new Formatter();
        fmt.format("| %-10s | %-12s | %-20s |\n", "Voltage", "Avg Resist", "Binary Currents");
        System.out.print(fmt);
        outLineLn();
    }

    @Override
    public void viewBody() {
        for (CircuitData item : getDataList()) {
            double avgR = 0;

            if (item.getResistances() != null) {
                for (double r : item.getResistances()) {
                    avgR += r;
                }
                avgR /= 4;
            }

            System.out.printf("| %-10.2f | %-12.2f | %-20s |\n",
                    item.getVoltage(),
                    avgR,
                    String.join(", ", item.getBinaryCurrents()));
        }
    }

    @Override
    public void viewFooter() {
        outLineLn();
        System.out.println("Items displayed: " + getDataList().size());
        outLineLn();
    }

    /**
     * Малює горизонтальну лінію розділювача.
     */
    protected void outLine() {
        for (int i = 0; i < width; i++) {
            System.out.print("-");
        }
    }

    /**
     * Малює горизонтальну лінію та переводить рядок.
     */
    protected void outLineLn() {
        outLine();
        System.out.println();
    }
}
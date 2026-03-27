package me.waxyach.practice.task4.command.type;

import lombok.Setter;

import me.waxyach.practice.task1.CircuitData;
import me.waxyach.practice.task4.command.Command;

/** Команда для зміни параметрів об'єкта (масштабування напруги). */
public class ChangeItemCommand implements Command {

    @Setter
    private CircuitData item;

    private double lastVoltage;

    @Setter
    private double scaleFactor;

    @Override
    public void execute() {
        if (item != null) {
            lastVoltage = item.getVoltage();
            item.setVoltage(lastVoltage * scaleFactor);
            updateCurrents();
        }
    }

    @Override
    public void undo() {
        if (item != null) {
            item.setVoltage(lastVoltage);
            updateCurrents();
        }
    }

    private void updateCurrents() {
        String[] binary = new String[4];
        for (int i = 0; i < 4; i++) {
            binary[i] = Integer.toBinaryString((int) (item.getVoltage() / item.getResistances()[i]));
        }
        item.setBinaryCurrents(binary);
    }
}

package me.waxyach.practice.task1;

import lombok.Getter;

import java.io.*;

/**
 * Клас для обчислення струмів та керування серіалізацією.
 */
@Getter
public class Calculation {

    private static final String FILE_NAME = "CircuitData.bin";

    private CircuitData data;

    public Calculation() {
        this.data = new CircuitData();
    }

    /**
     * Обчислює струми для 4 паралельних опорів.
     * @param voltage напруга (U)
     * @param r1 перший опір
     * @param r2 другий опір
     * @param r3 третій опір
     * @param r4 четвертий опір
     */
    public void init(double voltage, double r1, double r2, double r3, double r4) {
        data.setVoltage(voltage);
        double[] rArray = {r1, r2, r3, r4};
        data.setResistances(rArray);

        String[] bCurrents = new String[4];
        for (int i = 0; i < rArray.length; i++) {
            double current = voltage / rArray[i];

            bCurrents[i] = Integer.toBinaryString((int) current);
        }
        data.setBinaryCurrents(bCurrents);
    }

    /** Виводить поточний стан об'єкта на екран. */
    public void show() {
        System.out.println(data);
    }

    /**
     * Зберігає стан об'єкта у файл.
     * @throws IOException помилка введення-виведення
     */
    public void save() throws IOException {
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            os.writeObject(data);
        }
    }

    /**
     * Відновлює стан об'єкта з файлу.
     * @throws Exception помилка відновлення
     */
    public void restore() throws Exception {
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            data = (CircuitData) is.readObject();
        }
    }
}
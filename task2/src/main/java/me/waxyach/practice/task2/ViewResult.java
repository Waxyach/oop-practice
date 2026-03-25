package me.waxyach.practice.task2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

import me.waxyach.practice.task1.CircuitData;

/**
 * Реалізація інтерфейсу View, що забезпечує обчислення,
 * сериалізацію та відображення результатів у вигляді колекції.
 */
public class ViewResult implements View {

    private static final String FILE_NAME = "CircuitData.bin";

    private static final int DEFAULT_SIZE = 5;

    @Getter
    private List<CircuitData> dataList = new ArrayList<>();

    /**
     * Заповнює колекцію новими випадковими сценаріями розрахунків.
     */
    @Override
    public void viewInit() {
        dataList.clear();

        for (int i = 0; i < DEFAULT_SIZE; i++) {
            dataList.add(createRandomCircuitData());
        }
    }

    /**
     * Створює та розраховує параметри одного випадкового ланцюга {@link CircuitData}.
     * @return об'єкт CircuitData.
     */
    private CircuitData createRandomCircuitData() {
        CircuitData scenario = new CircuitData();
        double u = Math.random() * 220 + 10;
        double[] r = {randomR(), randomR(), randomR(), randomR()};

        scenario.setVoltage(u);
        scenario.setResistances(r);

        String[] binary = new String[4];
        for (int j = 0; j < 4; j++) {
            binary[j] = Integer.toBinaryString((int) (u / r[j]));
        }
        scenario.setBinaryCurrents(binary);
        return scenario;
    }

    /**
     * Генерує випадкове значення опору в діапазоні від 1 до 51 Ом.
     * @return випадкове число double.
     */
    private double randomR() {
        return Math.random() * 50 + 1;
    }

    /**
     * Відображає поточний вміст колекції на екрані.
     */
    @Override
    public void viewShow() {
        System.out.println("\n--- Current Collection State ---");
        if (dataList.isEmpty()) {
            System.out.println("Collection is empty.");
        } else {
            dataList.forEach(System.out::println);
        }
        System.out.println("--------------------------------");
    }

    /**
     * Зберігає всю колекцію об'єктів у бінарний файл.
     * @throws IOException помилка при роботі з файлом.
     */
    @Override
    public void viewSave() throws IOException {
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            os.writeObject(dataList);
        }
    }

    /**
     * Відновлює колекцію об'єктів з файлу.
     * @throws Exception помилка під час десеріалізації даних.
     */
    @Override
    @SuppressWarnings("unchecked")
    public void viewRestore() throws Exception {
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            dataList = (List<CircuitData>) is.readObject();
        }
    }
}

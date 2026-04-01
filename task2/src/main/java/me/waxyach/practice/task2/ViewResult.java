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

    public static final String FILE_NAME = "CircuitData.bin";

    public static final int DEFAULT_SIZE = 5;

    @Getter
    private final int size;

    @Getter
    protected List<CircuitData> dataList = new ArrayList<>();

    public ViewResult() {
        this(DEFAULT_SIZE);
    }

    public ViewResult(int size) {
        this.size = size;
    }

    /**
     * Заповнює колекцію новими випадковими сценаріями розрахунків.
     */
    @Override
    public void viewInit() {
        dataList.clear();
        for (int i = 0; i < size; i++) {
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
        double[] r = new double[4];
        for (int i = 0; i < 4; i++) {
            r[i] = randomR();
        }
        scenario.setVoltage(u);
        scenario.setResistances(r);

        String[] binary = new String[4];
        for (int j = 0; j < 4; j++) {
            binary[j] = Integer.toBinaryString((int) (u / r[j]));
        }
        scenario.setBinaryCurrents(binary);
        return scenario;
    }

    /** Виводить заголовок результатів. */
    @Override
    public void viewHeader() {
        System.out.println("--- Calculation Results ---");
    }

    /** Виводить основну частину (вміст колекції). */
    @Override
    public void viewBody() {
        if (dataList.isEmpty()) {
            System.out.println("No data available.");
        } else {
            dataList.forEach(System.out::println);
        }
    }

    /** Виводить футер результатів. */
    @Override
    public void viewFooter() {
        System.out.println("--- End of Results ---");
    }

    /**
     * Викликає Header, Body та Footer, які можуть бути перевизначені в підкласах.
     */
    @Override
    public final void viewShow() {
        viewHeader();
        viewBody();
        viewFooter();
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

    /**
     * Генерує випадкове значення опору в діапазоні від 1 до 51 Ом.
     * @return випадкове число double.
     */
    private double randomR() {
        return Math.random() * 50 + 1;
    }
}

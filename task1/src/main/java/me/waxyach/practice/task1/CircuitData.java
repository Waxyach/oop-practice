package me.waxyach.practice.task1;

import java.io.Serial;
import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Клас для збереження параметрів ланцюга й результатів розрахунків.
 */
@Setter
@Getter
@ToString
public class CircuitData implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private double voltage;

    /** Масив з 4-х опорів (R1, R2, R3, R4). */
    private transient double[] resistances = new double[4];

    /** Результат: масив двійкових уявлень цілої частини струмів */
    private String[] binaryCurrents = new String[4];
}

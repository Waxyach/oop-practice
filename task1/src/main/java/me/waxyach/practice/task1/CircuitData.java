package me.waxyach.practice.task1;

import java.io.Serial;
import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Клас для збереження параметрів ланцюга й результатів розрахунків.
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class CircuitData implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Displayable(columnName = "Напруга (U)")
    private double voltage;

    /** Масив з 4-х опорів (R1, R2, R3, R4). */
    @Displayable(columnName = "Опори (R1-R4)")
    private transient double[] resistances = new double[4];

    /** Результат: масив двійкових уявлень цілої частини струмів */
    @Displayable(columnName = "Струми (двійк.)")
    private String[] binaryCurrents = new String[4];
}
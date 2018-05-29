package it.polimi.se2018.model;

import java.io.Serializable;
import java.util.Objects;

public class WindowCell implements Serializable {
    public final Color color;
    public final int number;

    public WindowCell(Color color, int number) {
        this.color = color;
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WindowCell that = (WindowCell) o;
        return number == that.number &&
                color == that.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, number);
    }
}

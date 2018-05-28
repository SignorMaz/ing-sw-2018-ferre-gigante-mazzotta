package it.polimi.se2018.model;

import java.io.Serializable;

public class WindowCell implements Serializable {
    public final Color color;
    public final int number;

    public WindowCell(Color color, int number) {
        this.color = color;
        this.number = number;
    }
}

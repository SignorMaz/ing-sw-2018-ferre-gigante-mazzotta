package it.polimi.se2018.model;

public class Dice {
    private final Color color;
    private int number;

    public Dice(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}

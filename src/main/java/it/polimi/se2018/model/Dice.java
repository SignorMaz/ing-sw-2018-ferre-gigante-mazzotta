package it.polimi.se2018.model;

import java.io.Serializable;

import java.util.Random;

public class Dice implements Serializable {
    private final Color color;
    private int number;

    /**
     * create the object dice and set the color
     * @param color to set
     */
    public Dice(Color color) {
        if (color.equals(Color.BLANK)) {
            throw new IllegalArgumentException("Invalid color");
        }
        this.color = color;
        setRandomNumber();
    }

    public Dice(Color color, int number) {
        if (color.equals(Color.BLANK)) {
            throw new IllegalArgumentException("Invalid color");
        }
        this.color = color;
        setNumber(number);
    }

    /**
     * return the dice color
     * @return dice color
     */
        public Color getColor() {
        return color;
    }

    /**
     * return the dice number
     * @return dice number
     */
    public int getNumber() {
        return number;
    }

    /**
     * set the dice number
     * @param number number to set
     */
    public void setNumber(int number) {
        if (number < 1 || number > 6) {
            throw new IllegalArgumentException("Invalid number");
        }
        this.number = number;
    }

    /**
     * set a random dice number
     */
    public void setRandomNumber() {
        setNumber(new Random().nextInt(6) + 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dice dice = (Dice) o;

        if (number != dice.number) return false;
        return color == dice.color;
    }

    @Override
    public int hashCode() {
        int result = color != null ? color.hashCode() : 0;
        result = 31 * result + number;
        return result;
    }

    @Override
    public String toString() {
        return "Dice{" +
                "color=" + color +
                ", number=" + number +
                '}';
    }
}
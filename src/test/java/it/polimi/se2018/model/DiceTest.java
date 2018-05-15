package it.polimi.se2018.model;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class DiceTest {

    @Test(expected = IllegalArgumentException.class)
    public void invalidColor() {
        new Dice(Color.BLANK);
    }

    @Test
    public void validColor() {
        for (Color color : Color.values()) {
            if (color.equals(Color.BLANK)) {
                continue;
            }
            assertEquals(new Dice(color).getColor(), color);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidValue() {
        new Dice(Color.GREEN).setNumber(-1);
    }

    @Test
    public void validValue() {
        Dice dice = new Dice(Color.GREEN);
        for (int i = 1; i <= 6; i++) {
            dice.setNumber(i);
            assertEquals(dice.getNumber(), i);
        }
    }
}

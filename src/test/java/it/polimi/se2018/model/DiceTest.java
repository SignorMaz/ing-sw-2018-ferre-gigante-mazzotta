package it.polimi.se2018.model;

import org.junit.Test;

import static it.polimi.se2018.model.Color.BLANK;
import static it.polimi.se2018.model.Color.GREEN;
import static it.polimi.se2018.model.Color.PURPLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DiceTest {

    @Test(expected = IllegalArgumentException.class)
    public void invalidColor() {
        new Dice(BLANK);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidColor2() {
        new Dice(BLANK, 2);
    }

    @Test
    public void validColor() {
        for (Color color : Color.values()) {
            if (color.equals(BLANK)) {
                continue;
            }
            assertEquals(new Dice(color).getColor(), color);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidValue() {
        new Dice(GREEN).setNumber(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidValue2() {
        new Dice(GREEN).setNumber(7);
    }

    @Test
    public void validValue() {
        Dice dice = new Dice(GREEN);
        for (int i = 1; i <= 6; i++) {
            dice.setNumber(i);
            assertEquals(dice.getNumber(), i);
        }
    }

    @Test
    public void equalsTest() {
        Dice dice1 = new Dice(GREEN, 6);
        Dice dice2 = new Dice(GREEN, 6);
        assertEquals(dice1, dice2);
        Dice dice3 = new Dice(GREEN, 3);
        Dice dice4 = new Dice(PURPLE, 6);
        assertNotEquals(dice3, dice4);

    }

    @Test
    public void STRINGTest() {
        Dice dice1 = new Dice(GREEN, 6);
        assertEquals("Dice{color=GREEN, number=6}", dice1.toString());

    }
}

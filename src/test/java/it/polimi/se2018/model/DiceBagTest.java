package it.polimi.se2018.model;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class DiceBagTest {

    private static final int TOTAL_DICE_NUM = 90;
    private static final int DICE_PER_COLOR = 18;

    @Test
    public void totalDrawsNum() {
        DiceBag diceBag = new DiceBag();
        for (int i = 0; i < TOTAL_DICE_NUM; i++) {
            diceBag.drawDice();
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void tooManyDraws() {
        DiceBag diceBag = new DiceBag();
        for (int i = 0; i < TOTAL_DICE_NUM; i++) {
            diceBag.drawDice();
        }
        diceBag.drawDice();
    }

    @Test
    public void dicesPerColor() {
        DiceBag diceBag = new DiceBag();
        List<Dice> dices = new LinkedList<>();
        for (int i = 0; i < TOTAL_DICE_NUM; i++) {
            dices.add(diceBag.drawDice());
        }

        Map<Color, Integer> colorMap = new HashMap<>();
        for (Color color : Color.values()) {
            colorMap.put(color, 0);
        }
        for (Dice dice: dices) {
            colorMap.put(dice.getColor(), colorMap.get(dice.getColor()) + 1);
        }
        for (Color color : Color.values()) {
            if (color.equals(Color.BLANK)) {
                assertEquals(colorMap.get(color).intValue(), 0);
            } else {
                assertEquals(colorMap.get(color).intValue(), DICE_PER_COLOR);
            }
        }
    }
}

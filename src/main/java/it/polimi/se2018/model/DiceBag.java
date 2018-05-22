package it.polimi.se2018.model;

import java.util.LinkedList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DiceBag {
    private static final int DICES_PER_COLOR = 18;

    private final List<Dice> dices;

    /**
     * initialize 18 dices for color
     */
    public DiceBag() {
        dices = new LinkedList<>();
        for (Color color: Color.values()) {
            if (color.equals(Color.BLANK)){
                continue;
            }
            for (int i = 0; i < DICES_PER_COLOR; i++) {
                dices.add(new Dice(color));
            }
        }
        Collections.shuffle(dices);
    }

    /**
     * return a dice and remove it from the list
     * @return dice from the list
     */
    public Dice drawDice() {
        return dices.remove(0);
    }

    public void addDice(Dice dice) {
        int randomPosition = new Random().nextInt(dices.size());
        dices.add(randomPosition, dice);
    }
}
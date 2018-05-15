package it.polimi.se2018.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DiceBag {
    private static final int DICES_PER_COLOR = 18;

    private final List<Dice> dices;

    public DiceBag() {
        dices = new ArrayList<>();
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

    public Dice drawDice() {
        return dices.remove(0);
    }
}

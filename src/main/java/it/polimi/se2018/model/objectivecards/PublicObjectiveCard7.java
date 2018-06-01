package it.polimi.se2018.model.objectivecards;

import it.polimi.se2018.model.*;

public class PublicObjectiveCard7 implements ObjectiveCard {
    @Override
    public String getName() {
        return "Sfumature scure";
    }

    @Override
    public String getDescription() {
        return "Set di 5 & 6 ovunque";
    }

    @Override
    public int getPoints(WindowFrame windowFrame) {
        int points = 0;
        int five = 0;
        int six = 0;
        for (int i = 0; i < WindowPattern.ROWS; i++) {
            for (int j = 0; j < WindowPattern.COLUMNS; j++) {
                Position position = new Position(i, j);
                Dice dice = windowFrame.getPlacedDices().get(position);
                if (dice == null) {
                    continue;
                }
                int value = dice.getNumber();
                if (value == 5) {
                    five++;
                }
                if (value == 6) {
                    six++;
                }
            }
        }
        if (five < six) {
            points += five * 2;
        }
        if (six <= five) {
            points += six * 2;
        }
        return points;
    }
}

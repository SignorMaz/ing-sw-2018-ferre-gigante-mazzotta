package it.polimi.se2018.model.objectivecards;

import it.polimi.se2018.model.*;


public class PublicObjectiveCard5 implements ObjectiveCard {
    @Override
    public String getName() {
        return "Sfumature chiare";
    }

    @Override
    public String getDescription() {
        return "Set di 1 & 2 ovunque";
    }

    @Override
    public int getPoints(WindowFrame windowFrame) {
        int points = 0;
        int one = 0;
        int two = 0;
        for (int i = 0; i < WindowPattern.ROWS; i++) {
            for (int j = 0; j < WindowPattern.COLUMNS; j++) {
                Position position = new Position(i, j);
                Dice dice = windowFrame.getPlacedDices().get(position);
                if (dice == null) {
                    break;
                }
                int value = dice.getNumber();
                if (value == 1) {
                    one++;
                }
                if (value == 2) {
                    two++;
                }
            }
        }
        if (one < two) {
            points += one*2;
        }
        if (two <= one) {
            points += two*2;
        }
        return points;
    }
}

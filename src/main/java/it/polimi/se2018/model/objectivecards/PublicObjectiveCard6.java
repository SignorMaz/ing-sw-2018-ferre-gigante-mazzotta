package it.polimi.se2018.model.objectivecards;

import it.polimi.se2018.model.*;

public class PublicObjectiveCard6 implements ObjectiveCard {
    @Override
    public String getName() {
        return "Sfumature medie";
    }

    @Override
    public String getDescription() {
        return "Set di 3 & 4 ovunque";
    }

    @Override
    public int getPoints(WindowFrame windowFrame) {
        int points = 0;
        int three = 0;
        int four = 0;
        for (int i = 0; i < WindowPattern.ROWS; i++) {
            for (int j = 0; j < WindowPattern.COLUMNS; j++) {
                Position position = new Position(i, j);
                Dice dice = windowFrame.getPlacedDices().get(position);
                if (dice == null) {
                    continue;
                }
                int value = dice.getNumber();
                if (value == 3) {
                    three++;
                }
                if (value == 4) {
                    four++;
                }
            }
        }
        if (three < four) {
            points += three * 2;
        }
        if (four <= three) {
            points += four * 2;
        }
        return points;
    }
}

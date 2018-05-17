package it.polimi.se2018.model.objectivecards;

import it.polimi.se2018.model.*;

import java.util.HashSet;
import java.util.Set;

public class PublicObjectiveCard2 implements ObjectiveCard {
    @Override
    public String getName() {
        return "Colori diversi - Colonna";
    }

    @Override
    public String getDescription() {
        return "Colonne senza colori ripetuti";
    }

    @Override
    public int getPoints(WindowFrame windowFrame) {
        int points = 0;
        int streak = 0;
        for (int j = 0; j < WindowPattern.COLUMNS; j++) {
            Set<Color> colors = new HashSet<>();
            streak = 0;
            for (int i = 0; i < WindowPattern.ROWS; i++) {
                Position position = new Position(j, i);
                Dice dice = windowFrame.getPlacedDices().get(position);
                if (dice == null) {
                    continue;
                }
                Color color = dice.getColor();
                if (colors.contains(color)) {
                    break;
                }
                colors.add(color);
                streak++;
            }
            if (streak == 4) {
                points += 5;
            }
        }
        return points;
    }
}

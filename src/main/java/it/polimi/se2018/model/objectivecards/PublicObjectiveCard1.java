package it.polimi.se2018.model.objectivecards;

import it.polimi.se2018.model.*;

import java.util.HashSet;
import java.util.Set;

public class PublicObjectiveCard1 implements ObjectiveCard {
    @Override
    public String getName() {
        return "Colori diversi - Riga";
    }

    @Override
    public String getDescription() {
        return "Righe senza colori ripetuti";
    }

    @Override
    public int getPoints(WindowFrame windowFrame) {
        int points = 0;
        int streak = 0;
        for (int i = 0; i < WindowPattern.ROWS; i++) {
            Set<Color> colors = new HashSet<>();
            streak = 0;
            for (int j = 0; j < WindowPattern.COLUMNS; j++) {
                Position position = new Position(i, j);
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
            if (streak == 5) {
                points += 6;
            }

        }
        return points;
    }
}

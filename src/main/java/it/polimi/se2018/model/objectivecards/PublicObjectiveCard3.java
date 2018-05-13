package it.polimi.se2018.model.objectivecards;

import it.polimi.se2018.model.*;

import java.util.HashSet;
import java.util.Set;

public class PublicObjectiveCard3 implements ObjectiveCard {
    @Override
    public String getName() {
        return "Sfumature diverse - Riga";
    }

    @Override
    public String getDescription() {
        return "Righe senza sfumature ripetute";
    }

    @Override
    public int getPoints(WindowFrame windowFrame) {
        int points = 0;
        int streak;
        for (int i = 0; i < WindowPattern.ROWS; i++) {
            Set<Integer> values = new HashSet<>();
            streak = 0;
            for (int j = 0; j < WindowPattern.COLUMNS; j++) {
                Position position = new Position(i, j);
                Dice dice = windowFrame.getPlacedDices().get(position);
                if (dice == null) {
                    break;
                }
                int value = dice.getNumber();
                if (values.contains(value)) {
                    break;
                }
                values.add(value);
                streak++;
            }
            if (streak == 5) {
                points += 5;
            }

        }
        return points;
    }
}

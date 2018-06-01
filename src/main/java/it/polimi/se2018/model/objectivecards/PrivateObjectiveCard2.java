package it.polimi.se2018.model.objectivecards;

import it.polimi.se2018.model.*;

import java.util.Map;

public class PrivateObjectiveCard2 implements ObjectiveCard {
    @Override
    public String getName() {
        return "Sfumature gialle";
    }

    @Override
    public String getDescription() {
        return "Somma dei valori su tutti i dadi gialli";
    }

    @Override
    public int getPoints(WindowFrame windowFrame) {
        int points = 0;
        for (Map.Entry<Position, Dice> entry : windowFrame.getPlacedDices().entrySet()) {
            Dice dice = entry.getValue();
            if (dice.getColor() == Color.YELLOW) {
                points += dice.getNumber();
            }
        }
        return points;
    }
}

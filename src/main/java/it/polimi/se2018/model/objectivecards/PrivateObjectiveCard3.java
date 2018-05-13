package it.polimi.se2018.model.objectivecards;

import it.polimi.se2018.model.Color;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.ObjectiveCard;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.model.WindowFrame;

import java.awt.*;
import java.util.Map;

public class PrivateObjectiveCard3 implements ObjectiveCard{


    @Override
    public String getName() {
        return "Sfumature verdi";
    }

    @Override
    public String getDescription() {
        return "Somma dei valori su tutti i dadi verdi";
    }

    @Override
    public int getPoints(WindowFrame windowFrame) {
        int points = 0;
        for (Map.Entry<Position, Dice> entry: windowFrame.getPlacedDices().entrySet()) {
            Dice dice = entry.getValue();
            if (dice.getColor() == Color.GREEN) {
                points += dice.getNumber();
            }
        }
        return points;
    }
}

package it.polimi.se2018.model.objectivecards;

import it.polimi.se2018.model.*;

import java.util.Map;

public class PublicObjectiveCard9 implements ObjectiveCard {
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
        Map<Position, Dice> dices = windowFrame.getPlacedDices();
        for (int row = 0; row < WindowPattern.ROWS; row++) {
            for (int column = 0; column < WindowPattern.COLUMNS; column++) {
                Dice dice = dices.get(new Position(row, column));
                if (dice == null) {
                    continue;
                }

                Dice prevRowLeft = dices.get(new Position(row - 1, column - 1));
                Dice prevRowRight = dices.get(new Position(row - 1, column + 1));
                Dice nextRowLeft = dices.get(new Position(row + 1, column - 1));
                Dice nextRowRight = dices.get(new Position(row + 1, column + 1));
                if ((prevRowLeft != null && prevRowLeft.getColor() == dice.getColor()) ||
                        (prevRowRight != null && prevRowRight.getColor() == dice.getColor()) ||
                        (nextRowLeft != null && nextRowLeft.getColor() == dice.getColor()) ||
                        (nextRowRight != null && nextRowRight.getColor() == dice.getColor())) {
                    points++;
                }
            }
        }

        return points;
    }
}

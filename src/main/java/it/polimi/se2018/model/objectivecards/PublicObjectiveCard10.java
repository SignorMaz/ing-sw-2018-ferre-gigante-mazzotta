package it.polimi.se2018.model.objectivecards;

import it.polimi.se2018.model.*;

public class PublicObjectiveCard10 implements ObjectiveCard {
    @Override
    public String getName() {
        return "Varietà di colore";
    }

    @Override
    public String getDescription() {
        return "Set di dadi di ogni colore ovunque";
    }

    @Override
    public int getPoints(WindowFrame windowFrame) {
        int points;
        int min;
        int yellow = 0;
        int green = 0;
        int blue = 0;
        int purple = 0;
        int red = 0;
        for (int i = 0; i < WindowPattern.ROWS; i++) {
            for (int j = 0; j < WindowPattern.COLUMNS; j++) {
                Position position = new Position(i, j);
                Dice dice = windowFrame.getPlacedDices().get(position);
                if (dice == null) {
                    continue;
                }
                Color color = dice.getColor();
                switch (color) {
                    case YELLOW:
                        yellow++;
                        break;
                    case GREEN:
                        green++;
                        break;
                    case BLUE:
                        blue++;
                        break;
                    case PURPLE:
                        purple++;
                        break;
                    case RED:
                        red++;
                        break;

                    default:
                        break;

                }
            }
        }
        min = yellow;
        if (green < min) {
            min = green;
        }
        if (blue < min) {
            min = blue;
        }
        if (purple < min) {
            min = purple;
        }
        if (red < min) {
            min = red;
        }
        points = min * 4;
        return points;
    }
}

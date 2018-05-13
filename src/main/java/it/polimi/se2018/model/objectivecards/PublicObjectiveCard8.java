package it.polimi.se2018.model.objectivecards;

import it.polimi.se2018.model.*;

public class PublicObjectiveCard8 implements ObjectiveCard {

    @Override
    public String getName() {
        return "Sfumature diverse";
    }

    @Override
    public String getDescription() {
        return "Set di dadi di ogni valore ovunque";
    }

    @Override
    public int getPoints(WindowFrame windowFrame) {
        int points;
        int min;
        int one = 0;
        int two = 0;
        int three = 0;
        int four = 0;
        int five = 0;
        int six = 0;
        for (int i = 0; i < WindowPattern.ROWS; i++) {
            for (int j = 0; j < WindowPattern.COLUMNS; j++) {
                Position position = new Position(i, j);
                Dice dice = windowFrame.getPlacedDices().get(position);
                if (dice == null) {
                    break;
                }
                int value = dice.getNumber();
                switch (value) {
                    case 1:
                        one ++;
                        break;
                    case 2:
                        two ++;
                        break;
                    case 3:
                        three ++;
                        break;
                    case 4:
                        four ++;
                        break;
                    case 5:
                        five ++;
                        break;
                    case 6:
                        six ++;
                        break;
                        default: break;
                }
            }
        }
        min = one;
        if (two < min) {
            min = two;
        }
        if (three < min) {
            min = three;
        }
        if (four < min) {
            min = four;
        }
        if (five < min) {
            min = five;
        }
        if (six < min) {
            min = six;
        }
        points = min*5;
        return points;
    }
}

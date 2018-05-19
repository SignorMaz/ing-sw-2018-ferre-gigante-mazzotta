package it.polimi.se2018.model.toolcards;

import it.polimi.se2018.model.ToolCard;

public class ToolCard1 extends ToolCard {

    @Override
    public String getName() {
        return "Pinza Sgrossatrice";
    }

    @Override
    public String getDescription() {
        return "Dopo aver scelto un dado, aumenta o diminuisci il valore del dado scelto di 1. Non puoi cambiare un 6 in 1 o un 1 in 6";
    }

    @Override
    public int getNumber() {
        return 1;
    }

    @Override
    public boolean canChangeDiceValue(int value, boolean increase) {
        return (value != 1 || increase) && (value != 6 || !increase);
    }
}

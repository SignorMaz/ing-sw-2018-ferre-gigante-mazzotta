package it.polimi.se2018.model.toolcards;

import it.polimi.se2018.model.ToolCard;

public class ToolCard3 extends ToolCard {

    @Override
    public String getName() {
        return "Alesatore per lamina di rame";
    }

    @Override
    public String getDescription() {
        return "Muovi un qualsiasi dado nella tua vetrata ignorando le restrizioni di valore. Devi rispettare tutte le altre restrizioni di piazzamento.";
    }

    @Override
    public int getNumber() {
        return 3;
    }

    @Override
    public boolean ignoreNumber() {
        return true;
    }

    @Override
    public boolean canMovePlacedDice() {
        return true;
    }

}

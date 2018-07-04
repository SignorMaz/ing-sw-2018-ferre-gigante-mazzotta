package it.polimi.se2018.model.toolcards;

import it.polimi.se2018.model.ToolCard;

public class ToolCard2 extends ToolCard {

    @Override
    public String getName() {
        return "Pennello per Eglomise";
    }

    @Override
    public String getDescription() {
        return "Muovi un qualsiasi dado nella tua vetrata ignorando le restrizioni di colore. Devi rispettare tutte le altre restrizioni di piazzamento.";
    }

    @Override
    public int getNumber() {
        return 2;
    }

    @Override
    public boolean ignoreColor() {
        return true;
    }

    @Override
    public boolean canMovePlacedDice() {
        return true;
    }

}

package it.polimi.se2018.model.toolcards;

import it.polimi.se2018.model.ToolCard;

public class ToolCard4 extends ToolCard {

    @Override
    public String getName() {
        return "Lathekin";
    }

    @Override
    public String getDescription() {
        return "Muovi esattamente due dadi, rispettando tutte le restrizioni di piazzamento.";
    }

    @Override
    public int getNumber() {
        return 4;
    }
}

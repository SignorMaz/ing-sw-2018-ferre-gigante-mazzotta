package it.polimi.se2018.model.toolcards;

import it.polimi.se2018.model.ToolCard;

public class ToolCard12 extends ToolCard {

    @Override
    public String getName() {
        return "Taglierina Manuale";
    }

    @Override
    public String getDescription() {
        return "Muovi fino a due dadi dello stesso colore di un solo dado sul Tracciato dei Round. Devi rispettare tutte le restrizioni di piazzamento.";
    }

    @Override
    public int getNumber() {
        return 12;
    }

    @Override
    public boolean canMovePlacedDice() {
        return true;
    }
}

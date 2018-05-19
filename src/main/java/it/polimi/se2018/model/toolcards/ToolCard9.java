package it.polimi.se2018.model.toolcards;

import it.polimi.se2018.model.ToolCard;

public class ToolCard9 extends ToolCard {

    @Override
    public String getName() {
        return "Riga in Sughero";
    }

    @Override
    public String getDescription() {
        return "Dopo aver scelto un dado, piazzalo in una casella che non sia adiacente a un altro dado. Devi rispettare tutte le restrizioni di piazzamento.";
    }

    @Override
    public int getNumber() {
        return 9;
    }
}

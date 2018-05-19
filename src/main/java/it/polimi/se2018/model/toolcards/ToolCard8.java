package it.polimi.se2018.model.toolcards;

import it.polimi.se2018.model.ToolCard;

public class ToolCard8 extends ToolCard {

    @Override
    public String getName() {
        return "Tenaglia a Rotelle";
    }

    @Override
    public String getDescription() {
        return "Dopo il tuo primo turno scegli immediatamente un altro dado. Salta il tuo secondo turno in questo round.";
    }

    @Override
    public int getNumber() {
        return 8;
    }
}

package it.polimi.se2018.model.toolcards;

import it.polimi.se2018.model.ToolCard;

public class ToolCard6 extends ToolCard {

    @Override
    public String getName() {
        return "Pennello per Pasta Salda";
    }

    @Override
    public String getDescription() {
        return "Dopo aver scelto un dado, tira nuovamente quel dado. Se non puoi piazzarlo, riponilo nella Riserva.";
    }

    @Override
    public int getNumber() {
        return 6;
    }
}

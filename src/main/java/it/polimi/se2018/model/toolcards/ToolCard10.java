package it.polimi.se2018.model.toolcards;

import it.polimi.se2018.model.ToolCard;

public class ToolCard10 extends ToolCard {

    @Override
    public String getName() {
        return "Tampone Diamantato";
    }

    @Override
    public String getDescription() {
        return "Dopo aver scelto un dado, giralo sulla faccia opposta 6 diventa 1, 5 diventa 2, 4 diventa 3 ecc.";
    }

    @Override
    public int getNumber() {
        return 10;
    }

    @Override
    public boolean canFlipDice() {
        return true;
    }
}

package it.polimi.se2018.model.toolcards;

import it.polimi.se2018.model.ToolCard;

public class ToolCard5 extends ToolCard {

    @Override
    public String getName() {
        return "Taglierina circolare";
    }

    @Override
    public String getDescription() {
        return "Dopo aver scelto un dado, scambia quel dado con un dado sul Tracciato dei Round.";
    }

    @Override
    public int getNumber() {
        return 5;
    }

    @Override
    public boolean canSwapDices() {
        return true;
    }
}

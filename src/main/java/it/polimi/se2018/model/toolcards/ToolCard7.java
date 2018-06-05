package it.polimi.se2018.model.toolcards;

import it.polimi.se2018.model.ToolCard;

public class ToolCard7 extends ToolCard {

    @Override
    public String getName() {
        return "Martelletto";
    }

    @Override
    public String getDescription() {
        return "Tira nuovamente tutti i dadi della Riserva. Questa carta può essera usata solo durante il tuo secondo turno, prima di scegliere il secondo dado.";
    }

    @Override
    public int getNumber() {
        return 7;
    }

    @Override
    public boolean canUseCard(boolean isFirstTurn, boolean moveDone) {
        return !isFirstTurn && moveDone;
    }

    @Override
    public boolean canShakeDices() {
        return true;
    }
}

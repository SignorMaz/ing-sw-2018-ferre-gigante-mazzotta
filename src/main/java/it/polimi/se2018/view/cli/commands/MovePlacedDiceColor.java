package it.polimi.se2018.view.cli.commands;

import it.polimi.se2018.model.ToolCard;
import it.polimi.se2018.view.cli.PlayerViewCli;

public class MovePlacedDiceColor extends MovePlacedDice {

    @Override
    public String getText() {
        return "Move placed dice (Pennello per Eglomise)";
    }

    @Override
    public boolean canPerform(PlayerViewCli view) {
        ToolCard toolCard = view.getPlayerViewBase().getToolCard();
        return view.isMyTurn() && toolCard != null && toolCard.ignoreColor();
    }
}


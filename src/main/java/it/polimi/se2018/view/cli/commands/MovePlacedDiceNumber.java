package it.polimi.se2018.view.cli.commands;

import it.polimi.se2018.controller.actions.MovePlacedDiceAction;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.model.ToolCard;
import it.polimi.se2018.view.cli.InputHelper;
import it.polimi.se2018.view.cli.InputResponse;
import it.polimi.se2018.view.cli.PlayerViewCli;

import java.util.Scanner;

public class MovePlacedDiceNumber extends MovePlacedDice {

    @Override
    public String getText() {
        return "Move placed dice (Alesatore per lamina di rame)";
    }

    @Override
    public boolean canPerform(PlayerViewCli view) {
        ToolCard toolCard = view.getPlayerViewBase().getToolCard();
        return view.isMyTurn() && toolCard != null && toolCard.ignoreNumber();
    }
}


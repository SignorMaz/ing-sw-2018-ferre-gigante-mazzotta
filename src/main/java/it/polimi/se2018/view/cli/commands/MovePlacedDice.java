package it.polimi.se2018.view.cli.commands;

import it.polimi.se2018.controller.actions.MovePlacedDiceAction;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.model.ToolCard;
import it.polimi.se2018.view.cli.InputHelper;
import it.polimi.se2018.view.cli.PlayerViewCli;

import java.util.Scanner;

public class MovePlacedDice implements Command {

    @Override
    public String getText() {
        return "Move placed dice";
    }

    @Override
    public void handle(PlayerViewCli view) {
        Scanner input = view.getScanner();

        System.out.println("Select a dice:");
        Position curPosition = InputHelper.choosePosition(input, view.getPlayerViewBase().getWindowFrame());
        Dice dice = view.getPlayerViewBase().getWindowFrame().getPlacedDices().get(curPosition);
        if (dice == null) {
            System.out.println("No dice found at the selected position");
            return;
        }

        System.out.println("Select the new position:");
        Position newPosition = InputHelper.choosePosition(input);

        ToolCard toolcard = view.getPlayerViewBase().getToolCard();
        if (!view.getPlayerViewBase().getWindowFrame().isPositionValid(dice, newPosition, toolcard)) {
            System.out.println("The move is not valid");
        } else {
            view.getPlayerViewBase().send(new MovePlacedDiceAction(curPosition, newPosition));
        }
    }

    @Override
    public boolean canPerform(PlayerViewCli view) {
        ToolCard toolCard = view.getPlayerViewBase().getToolCard();
        return view.isMyTurn() && toolCard != null && toolCard.canMovePlacedDice();
    }
}


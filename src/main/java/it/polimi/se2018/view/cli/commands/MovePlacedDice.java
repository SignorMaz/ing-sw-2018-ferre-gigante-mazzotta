package it.polimi.se2018.view.cli.commands;

import it.polimi.se2018.controller.actions.MovePlacedDiceAction;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.model.ToolCard;
import it.polimi.se2018.view.cli.InputHelper;
import it.polimi.se2018.view.cli.InputResponse;
import it.polimi.se2018.view.cli.PlayerViewCli;

import java.util.Scanner;

public abstract class MovePlacedDice implements Command {

    @Override
    public void handle(PlayerViewCli view) {
        Scanner input = view.getScanner();

        System.out.println("Select a dice:");
        InputResponse<Position> curPosition = InputHelper.choosePosition(input, view.getPlayerViewBase().getWindowFrame());
        if (!curPosition.isValid()) {
            return;
        }

        Dice dice = view.getPlayerViewBase().getWindowFrame().getPlacedDices().get(curPosition.getValue());
        if (dice == null) {
            System.out.println("No dice found at the selected position");
            return;
        }

        System.out.println("Select the new position:");
        InputResponse<Position> newPosition = InputHelper.choosePosition(input);
        if (!newPosition.isValid()) {
            return;
        }

        ToolCard toolcard = view.getPlayerViewBase().getToolCard();
        if (!view.getPlayerViewBase().getWindowFrame().isPositionValid(dice, newPosition.getValue(), toolcard)) {
            System.out.println("The move is not valid");
        } else {
            view.getPlayerViewBase().send(new MovePlacedDiceAction(curPosition.getValue(), newPosition.getValue()));
        }
    }
}


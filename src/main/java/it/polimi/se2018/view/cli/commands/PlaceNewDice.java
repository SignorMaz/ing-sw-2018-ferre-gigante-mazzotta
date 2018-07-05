package it.polimi.se2018.view.cli.commands;

import it.polimi.se2018.controller.actions.PlaceNewDiceAction;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.model.ToolCard;
import it.polimi.se2018.view.cli.InputHelper;
import it.polimi.se2018.view.cli.InputResponse;
import it.polimi.se2018.view.cli.PlayerViewCli;

import java.util.Scanner;

public class PlaceNewDice implements Command {

    @Override
    public String getText() {
        return "Place new dice (Diluente per Pasta Salda)";
    }

    @Override
    public void handle(PlayerViewCli view) {
        Scanner input = view.getScanner();

        Dice newDice = view.getPlayerViewBase().getNewDice();
        if (newDice == null) {
            System.out.println("No dice found");
            return;
        }

        System.out.println("Choose a position for the dice");
        InputResponse<Position> position = InputHelper.choosePosition(input, view.getPlayerViewBase().getWindowFrame());
        if (!position.isValid()) {
            return;
        }

        System.out.println("Choose a value for the dice, color is " + newDice.getColor());
        InputResponse<Integer> valueResponse = InputHelper.getInt(input, 1, 6);
        if (!valueResponse.isValid()) {
            return;
        }

        ToolCard toolcard = view.getPlayerViewBase().getToolCard();
        if (!view.getPlayerViewBase().getWindowFrame().isPositionValid(newDice, position.getValue(), toolcard)) {
            System.out.println("The move is not valid");
        } else {
            view.getPlayerViewBase().send(new PlaceNewDiceAction(valueResponse.getValue(), position.getValue()));
        }
    }

    @Override
    public boolean canPerform(PlayerViewCli view) {
        boolean hasNewDice = view.getPlayerViewBase().getNewDice() != null;
        return view.isMyTurn() && hasNewDice;
    }
}

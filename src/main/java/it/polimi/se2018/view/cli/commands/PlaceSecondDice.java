package it.polimi.se2018.view.cli.commands;

import it.polimi.se2018.controller.actions.PlaceSecondDiceAction;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.model.ToolCard;
import it.polimi.se2018.view.cli.InputHelper;
import it.polimi.se2018.view.cli.InputResponse;
import it.polimi.se2018.view.cli.PlayerViewCli;
import it.polimi.se2018.view.cli.commands.Command;

import java.util.List;
import java.util.Scanner;

public class PlaceSecondDice implements Command {

    @Override
    public String getText() {
        return "Place second dice (Tenaglia a Rotelle)";
    }

    @Override
    public void handle(PlayerViewCli view) {
        Scanner input = view.getScanner();

        List<Dice> draftPool = view.getPlayerViewBase().getDraftPool();
        System.out.println("Dices:");
        InputResponse<Dice> dice = InputHelper.chooseDice(input, draftPool);
        if (!dice.isValid()) {
            return;
        }

        InputResponse<Position> position = InputHelper.choosePosition(input, view.getPlayerViewBase().getWindowFrame());
        if (!position.isValid()) {
            return;
        }
        ToolCard toolcard = view.getPlayerViewBase().getToolCard();
        if (!view.getPlayerViewBase().getWindowFrame().isPositionValid(dice.getValue(), position.getValue(), toolcard)) {
            System.out.println("The move is not valid");
        } else {
            view.getPlayerViewBase().send(new PlaceSecondDiceAction(position.getValue(), dice.getValue()));
        }
    }

    @Override
    public boolean canPerform(PlayerViewCli view) {
        ToolCard toolCard = view.getPlayerViewBase().getToolCard();
        return view.isMyTurn() && toolCard != null && toolCard.canPlaceTwoDices();
    }
}

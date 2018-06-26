package it.polimi.se2018.view.cli.commands;

import it.polimi.se2018.controller.actions.PlaceDiceAction;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.model.ToolCard;
import it.polimi.se2018.view.cli.InputHelper;
import it.polimi.se2018.view.cli.PlayerViewCli;

import java.util.List;
import java.util.Scanner;

public class PlaceNotAdjacentDice implements Command {

    @Override
    public String getText() {
        return "Place non-adjacent dice";
    }

    @Override
    public void handle(PlayerViewCli view) {
        Scanner input = view.getScanner();

        List<Dice> draftPool = view.getPlayerViewBase().getDraftPool();
        System.out.println("Dices:");
        Dice dice = InputHelper.chooseDraftPoolDice(input, draftPool);
        if (dice == null) {
            return;
        }

        Position position = InputHelper.choosePosition(input, view.getPlayerViewBase().getWindowFrame());

        ToolCard toolcard = view.getPlayerViewBase().getToolCard();
        if (!view.getPlayerViewBase().getWindowFrame().isPositionValid(dice, position, toolcard)) {
            System.out.println("The move is not valid");
        } else {
            view.getPlayerViewBase().send(new PlaceDiceAction(dice, position));
        }
    }

    @Override
    public boolean canPerform(PlayerViewCli view) {
        ToolCard toolCard = view.getPlayerViewBase().getToolCard();
        return view.isMyTurn() && toolCard != null && toolCard.notAdjacent();
    }
}

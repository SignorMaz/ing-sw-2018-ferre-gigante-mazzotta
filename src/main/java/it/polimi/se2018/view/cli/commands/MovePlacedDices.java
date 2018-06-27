package it.polimi.se2018.view.cli.commands;

import it.polimi.se2018.controller.actions.MovePlacedDicesAction;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.model.ToolCard;
import it.polimi.se2018.view.cli.InputHelper;
import it.polimi.se2018.view.cli.PlayerViewCli;

import java.util.Scanner;

public class MovePlacedDices implements Command {

    @Override
    public String getText() {
        return "Move placed dice";
    }

    @Override
    public void handle(PlayerViewCli view) {
        Scanner input = view.getScanner();

        System.out.println("Select a dice:");
        Position curPosition1 = InputHelper.choosePosition(input, view.getPlayerViewBase().getWindowFrame());
        Dice dice1 = view.getPlayerViewBase().getWindowFrame().getPlacedDices().get(curPosition1);
        if (dice1 == null) {
            System.out.println("No dice found at the selected position");
            return;
        }

        System.out.println("Select the new position:");
        Position newPosition1 = InputHelper.choosePosition(input);

        System.out.println("Select a second dice:");
        Position curPosition2 = InputHelper.choosePosition(input, view.getPlayerViewBase().getWindowFrame());
        Dice dice2 = view.getPlayerViewBase().getWindowFrame().getPlacedDices().get(curPosition1);
        if (dice2 == null) {
            System.out.println("No dice found at the selected position");
            return;
        }

        System.out.println("Select the new position:");
        Position newPosition2 = InputHelper.choosePosition(input);

        ToolCard toolcard = view.getPlayerViewBase().getToolCard();
        if (!toolcard.canPlaceTwoDices() || dice1.equals(dice2) || newPosition1.equals(newPosition2)) {
            System.out.println("The move is not valid");
        } else {
            view.getPlayerViewBase().send(new MovePlacedDicesAction(curPosition1, newPosition1, curPosition2, newPosition2));
        }
    }

    @Override
    public boolean canPerform(PlayerViewCli view) {
        ToolCard toolCard = view.getPlayerViewBase().getToolCard();
        return view.isMyTurn() && toolCard != null && toolCard.canMovePlacedDice();
    }
}

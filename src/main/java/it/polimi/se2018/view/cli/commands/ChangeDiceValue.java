package it.polimi.se2018.view.cli.commands;

import it.polimi.se2018.controller.actions.ChangeDiceValueAction;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.model.ToolCard;
import it.polimi.se2018.view.cli.InputHelper;
import it.polimi.se2018.view.cli.PlayerViewCli;

import java.util.Scanner;

public class ChangeDiceValue implements Command {

    @Override
    public String getText() {
        return "Change dice value";
    }

    @Override
    public void handle(PlayerViewCli view) {
        Scanner input = view.getScanner();

        Position position = InputHelper.choosePosition(input, view.getPlayerViewBase().getWindowFrame());
        Dice dice = view.getPlayerViewBase().getWindowFrame().getPlacedDices().get(position);
        if (dice == null) {
            System.out.println("No dice found at the given position");
            return;
        }

        System.out.println("Increase the value?");
        boolean increase = InputHelper.yesOrNo(input);
        ToolCard toolcard = view.getPlayerViewBase().getToolCard();
        if (!toolcard.canChangeDiceValue(1, increase)) {
            System.out.println("The move is not valid");
        } else {
            view.getPlayerViewBase().send(new ChangeDiceValueAction(position, increase));
        }
    }

    @Override
    public boolean canPerform(PlayerViewCli view) {
        return view.isMyTurn();
    }
}
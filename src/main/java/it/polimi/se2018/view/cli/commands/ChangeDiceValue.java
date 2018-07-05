package it.polimi.se2018.view.cli.commands;

import it.polimi.se2018.controller.actions.ChangeDiceValueAction;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.model.ToolCard;
import it.polimi.se2018.view.cli.InputHelper;
import it.polimi.se2018.view.cli.InputResponse;
import it.polimi.se2018.view.cli.PlayerViewCli;

import java.util.Scanner;

public class ChangeDiceValue implements Command {

    @Override
    public String getText() {
        return "Change dice value (Pinza Sgrossatrice)";
    }

    @Override
    public void handle(PlayerViewCli view) {
        Scanner input = view.getScanner();

        InputResponse<Position> position = InputHelper.choosePosition(input, view.getPlayerViewBase().getWindowFrame());
        if (!position.isValid()) {
            return;
        }
        Dice dice = view.getPlayerViewBase().getWindowFrame().getPlacedDices().get(position.getValue());
        if (dice == null) {
            System.out.println("No dice found at the given position");
            return;
        }

        System.out.println("Increase the value?");
        InputResponse<Boolean> increase = InputHelper.yesOrNo(input);
        if (!increase.isValid()) {
            return;
        }
        ToolCard toolcard = view.getPlayerViewBase().getToolCard();
        if (!toolcard.canChangeDiceValue(1, increase.getValue())) {
            System.out.println("The move is not valid");
        } else {
            view.getPlayerViewBase().send(new ChangeDiceValueAction(position.getValue(), increase.getValue()));
        }
    }

    @Override
    public boolean canPerform(PlayerViewCli view) {
        // Use dummy values for canChangeDiceValue()
        ToolCard toolCard = view.getPlayerViewBase().getToolCard();
        return view.isMyTurn() && toolCard != null && toolCard.canChangeDiceValue(5, false);
    }
}
package it.polimi.se2018.view.cli.commands;

import it.polimi.se2018.controller.actions.FlipDiceAction;
import it.polimi.se2018.controller.actions.RepositionRethrownDiceAction;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.model.ToolCard;
import it.polimi.se2018.view.cli.InputHelper;
import it.polimi.se2018.view.cli.InputResponse;
import it.polimi.se2018.view.cli.PlayerViewCli;

import java.util.List;
import java.util.Scanner;

public class FlipDice implements Command {

    @Override
    public String getText() {
        return "Flip a dice";
    }

    @Override
    public void handle(PlayerViewCli view) {
        Scanner input = view.getScanner();

        System.out.println("Select a dice:");
        List<Dice> draftPool = view.getPlayerViewBase().getDraftPool();
        InputResponse<Dice> draftPoolDice = InputHelper.chooseDice(input, draftPool);
        if (!draftPoolDice.isValid()) {
            return;
        }

        view.getPlayerViewBase().send(new FlipDiceAction(draftPoolDice.getValue()));
    }

    @Override
    public boolean canPerform(PlayerViewCli view) {
        ToolCard toolcard = view.getPlayerViewBase().getToolCard();
        return view.isMyTurn() && toolcard != null && toolcard.canFlipDice();
    }
}
package it.polimi.se2018.view.cli.commands;

import it.polimi.se2018.controller.actions.RethrowDiceAction;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.ToolCard;
import it.polimi.se2018.view.cli.InputHelper;
import it.polimi.se2018.view.cli.InputResponse;
import it.polimi.se2018.view.cli.PlayerViewCli;

import java.util.List;
import java.util.Scanner;

public class RethrowDice implements Command {

    @Override
    public String getText() {
        return "Rethrow a dice";
    }

    @Override
    public void handle(PlayerViewCli view) {
        Scanner input = view.getScanner();

        List<Dice> draftPool = view.getPlayerViewBase().getDraftPool();
        System.out.println("Dices:");
        InputResponse<Dice> draftPoolDice = InputHelper.chooseDice(input, draftPool);

        if (!draftPoolDice.isValid()) {
            return;
        }

        view.getPlayerViewBase().send(new RethrowDiceAction(draftPoolDice.getValue()));
    }

    @Override
    public boolean canPerform(PlayerViewCli view) {
        ToolCard toolCard = view.getPlayerViewBase().getToolCard();
        return view.isMyTurn() && toolCard != null && toolCard.canRethrowDice();
    }
}

package it.polimi.se2018.view.cli.commands;

import it.polimi.se2018.controller.actions.SwapTrackDiceAction;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.ToolCard;
import it.polimi.se2018.view.cli.InputHelper;
import it.polimi.se2018.view.cli.InputResponse;
import it.polimi.se2018.view.cli.PlayerViewCli;

import java.util.List;
import java.util.Scanner;

public class SwapTrackDice implements Command {

    @Override
    public String getText() {
        return "Swap track dice";
    }

    @Override
    public void handle(PlayerViewCli view) {
        Scanner input = view.getScanner();

        System.out.println("Choose a track dice");
        List<Dice> dices = view.getPlayerViewBase().getTrackDices();
        InputResponse<Integer> response = InputHelper.chooseOption(input,
                dices, d -> d.getColor() + " " + d.getNumber());
        if (!response.isValid()) {
            return;
        }
        Dice trackDice = dices.get(response.getValue());

        System.out.println("Choose a dice from the draft pool");
        InputResponse<Dice> draftPoolDice = InputHelper.chooseDice(input, view.getPlayerViewBase().getDraftPool());
        if (!draftPoolDice.isValid()) {
            return;
        }

        view.getPlayerViewBase().send(new SwapTrackDiceAction(trackDice, draftPoolDice.getValue()));
    }

    @Override
    public boolean canPerform(PlayerViewCli view) {
        ToolCard toolCard = view.getPlayerViewBase().getToolCard();
        List<Dice> trackDices = view.getPlayerViewBase().getTrackDices();
        return view.isMyTurn() &&
                toolCard != null && toolCard.canSwapDices() &&
                trackDices != null && trackDices.size() > 0;
    }
}
package it.polimi.se2018.view.cli.commands;

import it.polimi.se2018.controller.actions.SwapTrackDiceAction;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.ToolCard;
import it.polimi.se2018.view.cli.InputHelper;
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
        int optionNum = InputHelper.chooseOption(input, dices, d -> d.getColor() + " " + d.getNumber(), true);
        Dice trackDice = dices.get(optionNum);

        System.out.println("Choose a dice from the draft pool");
        Dice draftPoolDice = InputHelper.chooseDraftPoolDice(input, view.getPlayerViewBase().getDraftPool());

        view.getPlayerViewBase().send(new SwapTrackDiceAction(trackDice, draftPoolDice));
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
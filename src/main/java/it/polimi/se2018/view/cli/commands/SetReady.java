package it.polimi.se2018.view.cli.commands;

import it.polimi.se2018.controller.actions.SetReadyAction;
import it.polimi.se2018.model.WindowFrame;
import it.polimi.se2018.model.WindowPattern;
import it.polimi.se2018.model.WindowPatternCard;
import it.polimi.se2018.view.cli.InputHelper;
import it.polimi.se2018.view.cli.InputResponse;
import it.polimi.se2018.view.cli.PlayerViewCli;
import it.polimi.se2018.view.cli.WindowFramePrinter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SetReady implements Command {
    @Override
    public String getText() {
        return "Choose a WindowFrame";
    }

    private InputHelper.OptionString<WindowPattern> converter = card -> {
        WindowFrame tmpFrontFrame = new WindowFrame(card);
        return WindowFramePrinter.frameToString(tmpFrontFrame);
    };

    @Override
    public void handle(PlayerViewCli view) {
        Scanner input = view.getScanner();

        List<WindowPattern> cards = new ArrayList<>();
        for (WindowPatternCard card : view.getPlayerViewBase().getWindowPatternCards()) {
            cards.add(card.getFront());
            cards.add(card.getBack());
        }
        InputResponse<Integer> response = InputHelper.chooseOption(input, cards, converter);
        if (!response.isValid()) {
            return;
        }
        WindowPattern windowPattern = cards.get(response.getValue());
        view.getPlayerViewBase().setWindowFrame(windowPattern);
        view.getPlayerViewBase().send(new SetReadyAction(windowPattern));
        view.getPlayerViewBase().setReady(true);
    }

    @Override
    public boolean canPerform(PlayerViewCli view) {
        return view.isGameSetUp() && !view.getPlayerViewBase().isReady() && !view.getPlayerViewBase().isSuspended();
    }
}

package it.polimi.se2018.view.cli.commands;

import it.polimi.se2018.view.cli.DicePrinter;
import it.polimi.se2018.view.cli.PlayerViewCli;

public class ShowDraftPool implements Command {

    @Override
    public String getText() {
        return "Show draft pool";
    }

    @Override
    public void handle(PlayerViewCli view) {
        DicePrinter.printDraftPool(view.getPlayerViewBase().getDraftPool());
    }

    @Override
    public boolean canPerform(PlayerViewCli view) {
        return view.isGameSetUp() && view.isGameStarted();
    }
}
package it.polimi.se2018.view.cli.commands;

import it.polimi.se2018.controller.actions.CompleteTurnAction;
import it.polimi.se2018.view.cli.PlayerViewCli;

public class CompleteTurn implements Command {
    @Override
    public String getText() {
        return "End turn";
    }

    @Override
    public void handle(PlayerViewCli view) {
        view.getPlayerViewBase().send(new CompleteTurnAction());
    }

    @Override
    public boolean canPerform(PlayerViewCli view) {
        return view.isGameSetUp() && !view.isGameStarted() && view.getPlayerViewBase().isCurrentPlayer();
    }
}
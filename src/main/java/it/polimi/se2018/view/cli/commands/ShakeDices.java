package it.polimi.se2018.view.cli.commands;

import it.polimi.se2018.controller.actions.ShakeDicesAction;
import it.polimi.se2018.model.ToolCard;
import it.polimi.se2018.view.cli.InputHelper;
import it.polimi.se2018.view.cli.PlayerViewCli;

public class ShakeDices implements Command {

    @Override
    public String getText() {
        return "Shake dices";
    }

    @Override
    public void handle(PlayerViewCli view) {
        view.getPlayerViewBase().send(new ShakeDicesAction());
    }

    @Override
    public boolean canPerform(PlayerViewCli view) {
        ToolCard toolCard = view.getPlayerViewBase().getToolCard();
        return view.isMyTurn() && toolCard != null && toolCard.canShakeDices();
    }
}
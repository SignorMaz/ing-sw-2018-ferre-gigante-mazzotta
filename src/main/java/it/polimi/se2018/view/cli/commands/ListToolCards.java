package it.polimi.se2018.view.cli.commands;

import it.polimi.se2018.model.ToolCard;
import it.polimi.se2018.view.cli.PlayerViewCli;

public class ListToolCards implements Command {
    @Override
    public String getText() {
        return "List the toolcards available";
    }

    @Override
    public void handle(PlayerViewCli view) {
        for (ToolCard card : view.getPlayerViewBase().getToolCards()) {
            System.out.println(card.getName() + " - " + card.getDescription());
        }
    }

    @Override
    public boolean canPerform(PlayerViewCli view) {
        return view.isGameStarted();
    }
}

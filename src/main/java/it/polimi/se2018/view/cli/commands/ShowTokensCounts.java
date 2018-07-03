package it.polimi.se2018.view.cli.commands;

import it.polimi.se2018.view.cli.PlayerViewCli;

import java.util.Map;

public class ShowTokensCounts implements Command {

    @Override
    public String getText() {
        return "Show tokens count";
    }

    @Override
    public void handle(PlayerViewCli view) {
        System.out.println("Your tokens: " + view.getPlayerViewBase().getFavorTokens());

        for (Map.Entry<String, Integer> entry : view.getPlayerViewBase().getRivalsFavorTokens().entrySet()) {
            System.out.println(entry.getKey() + "'s tokens: " + entry.getValue());
        }
    }

    @Override
    public boolean canPerform(PlayerViewCli view) {
        return view.isGameStarted();
    }
}

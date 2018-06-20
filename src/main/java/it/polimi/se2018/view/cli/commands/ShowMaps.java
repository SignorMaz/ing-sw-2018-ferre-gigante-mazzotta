package it.polimi.se2018.view.cli.commands;

import it.polimi.se2018.view.PlayerView;
import it.polimi.se2018.view.cli.WindowFramePrinter;

public class ShowMaps implements Command {
    @Override
    public String getText() {
        return "Show maps";
    }

    @Override
    public void handle(PlayerView view) {
        System.out.println("Current player:");
        WindowFramePrinter.print(view.getPlayerViewBase().getWindowFrame());

        // TODO: Print frame of the other players
    }
}

package it.polimi.se2018.view.cli.commands;

import it.polimi.se2018.model.WindowFrame;
import it.polimi.se2018.view.PlayerView;
import it.polimi.se2018.view.PlayerViewBase;
import it.polimi.se2018.view.cli.WindowFramePrinter;

import java.util.Map;

public class ShowMaps implements Command {
    @Override
    public String getText() {
        return "Show maps";
    }

    @Override
    public void handle(PlayerView view) {
        System.out.println("Current player:");
        PlayerViewBase viewBase = view.getPlayerViewBase();
        WindowFramePrinter.print(viewBase.getWindowFrame());

        System.out.println("Rivals:");
        for (Map.Entry<String, WindowFrame> entry : viewBase.getRivalWindowFrames().entrySet()) {
            WindowFramePrinter.print(entry.getValue());
        }
    }
}

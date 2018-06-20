package it.polimi.se2018.view.cli.commands;

import it.polimi.se2018.view.PlayerView;

public interface Command {
    String getText();
    void handle(PlayerView view);
}


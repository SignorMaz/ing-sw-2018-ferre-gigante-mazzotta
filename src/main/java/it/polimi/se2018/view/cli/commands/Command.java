package it.polimi.se2018.view.cli.commands;

import it.polimi.se2018.view.cli.PlayerViewCli;

public interface Command {
    String getText();

    void handle(PlayerViewCli view);

    boolean canPerform(PlayerViewCli view);
}

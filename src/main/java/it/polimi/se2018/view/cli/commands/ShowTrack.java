package it.polimi.se2018.view.cli.commands;

import it.polimi.se2018.view.cli.DiceTrackPrinter;
import it.polimi.se2018.view.cli.PlayerViewCli;

public class ShowTrack implements Command {
    @Override
    public String getText() {
        return "Show track";
    }

    @Override
    public void handle(PlayerViewCli view) {
        DiceTrackPrinter.print(view.getPlayerViewBase().getTrackDices());
    }

    @Override
    public boolean canPerform(PlayerViewCli view) {
        return view.isGameSetUp() && view.isGameStarted();
    }
}
package it.polimi.se2018.controller.actions;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.WindowPattern;

public class SetReadyAction extends Action {

    private final WindowPattern windowPattern;

    public SetReadyAction(WindowPattern windowPattern) {
        this.windowPattern = windowPattern;
    }

    @Override
    public void perform(Player player) {
        player.setReady(windowPattern);
    }
}

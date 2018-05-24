package it.polimi.se2018.controller.actions;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.Player;

public class SetReadyAction extends Action {
    private final int windowFrameNumber;

    public SetReadyAction(String playerId, int windowFrameNumber) {
        super(playerId);
        this.windowFrameNumber = windowFrameNumber;
    }

    @Override
    public void doAction() {
        Player player = Controller.getInstance().getPlayer(getPlayerId());
        player.setReady(windowFrameNumber);
    }
}

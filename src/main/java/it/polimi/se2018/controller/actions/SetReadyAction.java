package it.polimi.se2018.controller.actions;

import it.polimi.se2018.model.Player;

public class SetReadyAction extends Action {
    private final int windowFrameNumber;

    private final boolean front;

    public SetReadyAction(int windowFrameNumber, boolean front) {
        this.windowFrameNumber = windowFrameNumber;
        this.front = front;
    }

    @Override
    public void perform(Player player) {
        player.setReady(windowFrameNumber, front);
    }
}

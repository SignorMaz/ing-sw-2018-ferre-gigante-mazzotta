package it.polimi.se2018.controller.events;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.WindowFrame;
import it.polimi.se2018.view.PlayerView;


public class WindowFrameChangedEvent extends Event {
    private final String ownerId;
    private final WindowFrame windowFrame;

    public WindowFrameChangedEvent(String playerId, Player owner) {
        super(playerId);
        this.ownerId = owner.getPlayerId();
        this.windowFrame = owner.getWindowFrame();
    }

    @Override
    public void update(PlayerView playerView) {
        playerView.onWindowFrameChanged(ownerId, windowFrame);
    }
}
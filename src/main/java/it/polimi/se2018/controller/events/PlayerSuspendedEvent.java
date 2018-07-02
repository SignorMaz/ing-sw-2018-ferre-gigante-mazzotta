package it.polimi.se2018.controller.events;

import it.polimi.se2018.view.PlayerView;

public class PlayerSuspendedEvent extends Event {

    public PlayerSuspendedEvent(String playerId) {
        super(playerId);
    }

    @Override
    public void update(PlayerView playerView) {
        playerView.onPlayerSuspended();
    }
}

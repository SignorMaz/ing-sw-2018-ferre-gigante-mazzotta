package it.polimi.se2018.controller.events;

import it.polimi.se2018.view.PlayerView;

public class NewTurnEvent extends Event {
    public NewTurnEvent(String playerId) {
        super(playerId);
    }

    @Override
    public void update(PlayerView playerView) {
    }
}

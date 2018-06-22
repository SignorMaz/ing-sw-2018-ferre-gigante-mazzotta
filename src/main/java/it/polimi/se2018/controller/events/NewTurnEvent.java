package it.polimi.se2018.controller.events;

import it.polimi.se2018.view.PlayerView;

public class NewTurnEvent extends Event {
    private final String currentPlayerId;

    public NewTurnEvent(String playerId, String currentPlayerId) {
        super(playerId);
        this.currentPlayerId = currentPlayerId;
    }

    @Override
    public void update(PlayerView playerView) {
        playerView.onNewTurn(currentPlayerId);
    }
}

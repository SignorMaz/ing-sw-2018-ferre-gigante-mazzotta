package it.polimi.se2018.controller.events;

import it.polimi.se2018.view.PlayerView;

public class GameOverEvent extends Event {

    public GameOverEvent(String playerId) {
        super(playerId);
    }

    @Override
    public void update(PlayerView playerView) {
        playerView.onGameOver();
    }
}

package it.polimi.se2018.controller.events;

import it.polimi.se2018.view.PlayerView;

public class GameOverEvent extends Event {

    private final boolean isGameOver;

    public GameOverEvent(String playerId, boolean isGameOver) {
        super(playerId);
        this.isGameOver = isGameOver;
    }

    @Override
    public void update(PlayerView playerView) {
        playerView.onGameOver();
    }
}

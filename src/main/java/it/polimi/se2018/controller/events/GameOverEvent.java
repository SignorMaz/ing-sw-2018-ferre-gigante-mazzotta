package it.polimi.se2018.controller.events;

public class GameOverEvent extends Event {

    private final boolean isGameOver;

    public GameOverEvent(String playerId, boolean isGameOver) {
        super(playerId);
        this.isGameOver = isGameOver;
    }

    @Override
    public void update() {
    }
}

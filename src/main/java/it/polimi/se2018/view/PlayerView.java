package it.polimi.se2018.view;

public abstract class PlayerView {

    private final String playerId;

    public PlayerView(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerId() {
        return playerId;
    }
}

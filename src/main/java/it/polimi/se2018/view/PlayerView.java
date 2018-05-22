package it.polimi.se2018.view;

import it.polimi.se2018.Observer;
import it.polimi.se2018.controller.events.Event;

public abstract class PlayerView implements Observer {

    private final String playerId;

    public PlayerView(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerId() {
        return playerId;
    }

        @Override
    public void send(Event event) {
            }
}

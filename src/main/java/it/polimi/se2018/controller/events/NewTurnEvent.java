package it.polimi.se2018.controller.events;

public class NewTurnEvent extends Event {
    public NewTurnEvent(String playerId) {
        super(playerId);
    }

    @Override
    public void update() {
    }
}

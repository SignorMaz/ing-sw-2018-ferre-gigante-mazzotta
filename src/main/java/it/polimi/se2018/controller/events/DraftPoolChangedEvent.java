package it.polimi.se2018.controller.events;

public class DraftPoolChangedEvent extends Event {
    public DraftPoolChangedEvent(String playerId) {
        super(playerId);
    }

    @Override
    public void update() {
    }
}

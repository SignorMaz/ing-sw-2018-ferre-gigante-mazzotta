package it.polimi.se2018.controller.events;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Position;

public class DicePlacedEvent extends Event {
    private final Dice dice;
    private final Position position;

    public DicePlacedEvent(String playerId, Dice dice, Position position) {
        super(playerId);
        this.dice = dice;
        this.position = position;
    }

    @Override
    public void update() {
    }
}

package it.polimi.se2018.controller.events;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.view.PlayerView;


public class DicePlacedEvent extends Event {
    private final Dice dice;
    private final Position position;
    private final String playerPlacingDice;

    public DicePlacedEvent(String playerId, String playerPlacingDice, Dice dice, Position position) {
        super(playerId);
        this.playerPlacingDice = playerPlacingDice;
        this.dice = dice;
        this.position = position;
    }

    @Override
    public void update(PlayerView playerView) {
        playerView.onDicePlaced(playerPlacingDice, position, dice);
    }
}



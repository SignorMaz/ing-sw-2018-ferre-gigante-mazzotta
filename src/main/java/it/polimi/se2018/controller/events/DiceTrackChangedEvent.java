package it.polimi.se2018.controller.events;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.view.PlayerView;

import java.util.ArrayList;
import java.util.List;


public class DiceTrackChangedEvent extends Event {
    private final ArrayList<Dice> track;

    public DiceTrackChangedEvent(String playerId, List<Dice> track) {
        super(playerId);
        this.track = new ArrayList<>(track);
    }

    @Override
    public void update(PlayerView playerView) {
        playerView.onDiceTrackChanged(track);
    }
}



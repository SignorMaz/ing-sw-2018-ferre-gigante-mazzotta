package it.polimi.se2018.controller.events;

import it.polimi.se2018.view.PlayerView;

public class PointsChangedEvent extends Event {
    private final int points;

    public PointsChangedEvent(String playerId, int points) {
        super(playerId);
        this.points = points;
    }

    @Override
    public void update(PlayerView playerView) {
    }
}

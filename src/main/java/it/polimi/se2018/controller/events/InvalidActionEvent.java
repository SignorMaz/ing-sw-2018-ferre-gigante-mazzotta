package it.polimi.se2018.controller.events;

import it.polimi.se2018.controller.actions.Action;
import it.polimi.se2018.view.PlayerView;

public class InvalidActionEvent extends Event {

    private final Action action;

    public InvalidActionEvent(String playerId, Action action) {
        super(playerId);
        this.action = action;
    }

    @Override
    public void update(PlayerView playerView) {
    }
}
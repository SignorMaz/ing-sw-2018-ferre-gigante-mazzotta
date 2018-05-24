package it.polimi.se2018.controller.events;

import it.polimi.se2018.view.PlayerView;

public class TokensChangedEvent extends Event {

    private final int tokens;

    public TokensChangedEvent(String playerId, int tokens) {
        super(playerId);
        this.tokens = tokens;
    }

    @Override
    public void update(PlayerView playerView) {
    }
}

package it.polimi.se2018.controller.events;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.view.PlayerView;

public class TokensChangedEvent extends Event {

    private final String ownerId;
    private final int tokens;

    public TokensChangedEvent(String playerId, Player owner) {
        super(playerId);
        this.ownerId = owner.getPlayerId();
        this.tokens = owner.getFavorTokensCount();
    }

    @Override
    public void update(PlayerView playerView) {
        playerView.onTokensChanged(ownerId, tokens);
    }
}

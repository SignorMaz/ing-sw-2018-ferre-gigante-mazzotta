package it.polimi.se2018.controller.events;

public class TokensChangedEvent extends Event {

    private final int tokens;

    public TokensChangedEvent(String playerId, int tokens) {
        super(playerId);
        this.tokens = tokens;
    }

    @Override
    public void update() {
    }
}

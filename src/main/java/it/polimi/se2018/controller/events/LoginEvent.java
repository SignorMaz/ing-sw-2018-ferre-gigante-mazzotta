package it.polimi.se2018.controller.events;

import it.polimi.se2018.view.PlayerView;

public class LoginEvent extends Event {

    private final boolean logged;

    public LoginEvent(String playerId, boolean logged) {
        super(playerId);
        this.logged = logged;
    }

    @Override
    public void update(PlayerView playerView) {
        playerView.onLogin(logged);
    }
}

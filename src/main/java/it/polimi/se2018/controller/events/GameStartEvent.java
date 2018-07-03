package it.polimi.se2018.controller.events;

import it.polimi.se2018.model.WindowFrame;
import it.polimi.se2018.view.PlayerView;

import java.util.HashMap;
import java.util.Map;

public class GameStartEvent extends Event {

    private final HashMap<String, WindowFrame> windowFrames;

    public GameStartEvent(String playerId,
                          Map<String, WindowFrame> windowFrames) {
        super(playerId);
        this.windowFrames = new HashMap<>(windowFrames);
    }

    @Override
    public void update(PlayerView playerView) {
        playerView.onGameStarted(windowFrames);
    }
}
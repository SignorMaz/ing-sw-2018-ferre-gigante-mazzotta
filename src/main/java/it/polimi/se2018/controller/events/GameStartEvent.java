package it.polimi.se2018.controller.events;

import it.polimi.se2018.model.WindowFrame;
import it.polimi.se2018.view.PlayerView;

import java.util.HashMap;
import java.util.Map;

public class GameStartEvent extends Event {

    private final HashMap<String, WindowFrame> windowFrames;
    private final HashMap<String, Integer> tokens;

    public GameStartEvent(String playerId,
                          Map<String, WindowFrame> windowFrames,
                          Map<String, Integer> tokens) {
        super(playerId);
        this.windowFrames = new HashMap<>(windowFrames);
        this.tokens = new HashMap<>(tokens);
    }

    @Override
    public void update(PlayerView playerView) {
        playerView.onGameStarted(windowFrames, tokens);
    }
}
package it.polimi.se2018.controller.events;

import it.polimi.se2018.model.WindowFrame;
import it.polimi.se2018.view.PlayerView;

import java.util.HashMap;
import java.util.Map;

public class GameStartEvent extends Event {

    private HashMap<String, WindowFrame> windowFramesMap;

    public GameStartEvent(String playerId,
                          Map<String, WindowFrame> windowFramesMap) {
        super(playerId);
        this.windowFramesMap = new HashMap<>(windowFramesMap);
    }

    @Override
    public void update(PlayerView playerView) {
        playerView.onGameStarted(windowFramesMap);
    }
}
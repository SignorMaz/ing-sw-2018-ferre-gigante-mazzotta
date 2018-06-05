package it.polimi.se2018.controller.events;

import it.polimi.se2018.model.WindowPattern;
import it.polimi.se2018.view.PlayerView;

import java.util.HashMap;
import java.util.Map;

public class GameStartEvent extends Event {

    private HashMap<String, WindowPattern> windowPatternMap;

    public GameStartEvent(String playerId,
                          Map<String, WindowPattern> windowPatternMap) {
        super(playerId);
        this.windowPatternMap = new HashMap<>(windowPatternMap);
    }

    @Override
    public void update(PlayerView playerView) {
        playerView.onGameStarted(windowPatternMap);
    }
}
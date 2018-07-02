package it.polimi.se2018.controller.events;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.view.PlayerView;

import java.util.*;

public class GameOverEvent extends Event {

    private HashMap<String, Integer> chart;

    public GameOverEvent(String playerId, List<Player> players) {
        super(playerId);
        chart = new HashMap<>();
        for (Player player : players) {
            chart.put(player.getPlayerId(), player.getPoints());
        }
    }

    @Override
    public void update(PlayerView playerView) {
        playerView.onGameOver(chart);
    }
}

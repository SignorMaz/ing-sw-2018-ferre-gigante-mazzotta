package it.polimi.se2018.controller.events;

import it.polimi.se2018.model.*;
import it.polimi.se2018.view.PlayerView;

import java.util.ArrayList;
import java.util.List;

public class InitialSetupEvent extends Event {

    // Player properties
    private Player.Color playerColor;
    private ArrayList<WindowPatternCard> windowPatternCards;
    private ArrayList<ObjectiveCard> privateObjectCards;
    private int favorTokens;

    // Game properties
    private ArrayList<ObjectiveCard> publicObjectCards;
    private ArrayList<ToolCard> toolCards;
    private int turnTimeout;
    private ArrayList<String> playerIds;

    public InitialSetupEvent(String playerId,
                             Player.Color playerColor,
                             List<WindowPatternCard> windowPatternCards,
                             List<ObjectiveCard> privateObjectCards,
                             int favorTokens,
                             List<ObjectiveCard> publicObjectCards,
                             List<ToolCard> toolCards,
                             int turnTimeout,
                             List<String> playerIds) {
        super(playerId);

        // List<T> is not Serializable. Allow to pass generic Lists and make them
        // Serializable creating new ArrayList<T> or other Serializable lists.

        this.playerColor = playerColor;
        this.windowPatternCards = new ArrayList<>(windowPatternCards);
        this.privateObjectCards = new ArrayList<>(privateObjectCards);
        this.favorTokens = favorTokens;

        this.publicObjectCards = new ArrayList<>(publicObjectCards);
        this.toolCards = new ArrayList<>(toolCards);
        this.turnTimeout = turnTimeout;
        this.playerIds = new ArrayList<>(playerIds);
    }

    @Override
    public void update(PlayerView playerView) {
        playerView.initialSetup(
                playerColor,
                windowPatternCards,
                privateObjectCards,
                favorTokens,
                publicObjectCards,
                toolCards,
                turnTimeout,
                playerIds
        );
    }
}

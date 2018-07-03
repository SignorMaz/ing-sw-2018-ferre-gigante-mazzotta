package it.polimi.se2018.controller.events;

import it.polimi.se2018.model.*;
import it.polimi.se2018.view.PlayerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InitialSetupEvent extends Event {

    public static final class Data implements Serializable {

        // Player properties
        public final Player.Color playerColor;
        public final ArrayList<WindowPatternCard> windowPatternCards;
        public final ArrayList<ObjectiveCard> privateObjectCards;

        // Game properties
        public final ArrayList<ObjectiveCard> publicObjectCards;
        public final ArrayList<ToolCard> toolCards;
        public final int turnTimeout;
        public final ArrayList<String> playerIds;

        public Data(Player.Color playerColor,
                    List<WindowPatternCard> windowPatternCards,
                    List<ObjectiveCard> privateObjectCards,
                    List<ObjectiveCard> publicObjectCards,
                    List<ToolCard> toolCards,
                    int turnTimeout,
                    List<String> playerIds) {
            // List<T> is not Serializable. Allow to pass generic Lists and make them
            // Serializable creating new ArrayList<T> or other Serializable lists.

            this.playerColor = playerColor;
            this.windowPatternCards = new ArrayList<>(windowPatternCards);
            this.privateObjectCards = new ArrayList<>(privateObjectCards);

            this.publicObjectCards = new ArrayList<>(publicObjectCards);
            this.toolCards = new ArrayList<>(toolCards);
            this.turnTimeout = turnTimeout;
            this.playerIds = new ArrayList<>(playerIds);
        }
    }

    private final Data data;

    public InitialSetupEvent(String playerId, Data data) {
        super(playerId);
        this.data = data;
    }

    @Override
    public void update(PlayerView playerView) {
        playerView.onInitialSetup(data);
    }
}
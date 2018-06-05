package it.polimi.se2018.view;

import it.polimi.se2018.controller.actions.Action;
import it.polimi.se2018.controller.events.Event;
import it.polimi.se2018.model.*;
import it.polimi.se2018.network.Client;
import it.polimi.se2018.network.ConnectionType;
import it.polimi.se2018.rmi.RmiClient;
import it.polimi.se2018.socket.SocketClient;
import it.polimi.se2018.util.Observer;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public abstract class PlayerView implements Observer {

    private static final Logger LOGGER = Logger.getLogger("PlayerView");
    private final String playerId;
    private final Client client;

    // Player properties
    private Player.Color playerColor;
    private List<WindowPatternCard> windowPatternCards;
    private WindowFrame windowFrame;
    private List<ObjectiveCard> privateObjectCards;
    private int favorTokens;

    // Game properties
    private List<ObjectiveCard> publicObjectCards;
    private List<ToolCard> toolCards;
    private int turnTimeout;
    private List<String> playerIds;
    private Map<String, WindowFrame> rivalFrames;

    public PlayerView(String playerId, ConnectionType connectionType) throws IOException, NotBoundException {
        this.playerId = playerId;
        if (connectionType.equals(ConnectionType.SOCKET)) {
            client = new SocketClient(this);
        } else {
            client = new RmiClient(this);
        }
    }

    public void login() throws IOException {
        client.login(getPlayerId());
    }

    public void logout() throws IOException {
        client.logout(getPlayerId());
    }

    public String getPlayerId() {
        return playerId;
    }

    @Override
    public void handle(Event event) {
        event.update(this);
    }

    public void send(Action action) {
        client.send(action);

    }

    public void initialSetup(Player.Color playerColor,
                             List<WindowPatternCard> windowPatternCards,
                             List<ObjectiveCard> privateObjectCards,
                             int favorTokens,
                             List<ObjectiveCard> publicObjectCards,
                             List<ToolCard> toolCards,
                             int turnTimeout,
                             List<String> playerIds) {
        this.playerColor = playerColor;
        this.turnTimeout = turnTimeout;
        this.windowPatternCards = windowPatternCards;
        this.privateObjectCards = privateObjectCards;
        this.favorTokens = favorTokens;

        this.publicObjectCards = publicObjectCards;
        this.toolCards = toolCards;
        this.playerIds = playerIds;

        onInitialSetup();
    }

    public void onGameStarted(Map<String, WindowPattern> windowPatternMap) {
        for (Map.Entry<String, WindowPattern> entry : windowPatternMap.entrySet()) {
            String rivalId = entry.getKey();
            WindowFrame rivalWindowFrame = new WindowFrame(entry.getValue());
            rivalFrames.put(rivalId, rivalWindowFrame);
        }

        onGameStarted();
    }

    public Map<String, WindowFrame> getRivalFrames() {
        return rivalFrames;
    }


    public Player.Color getPlayerColor() {
        return playerColor;
    }

    public List<WindowPatternCard> getWindowPatternCards() {
        return windowPatternCards;
    }

    public List<ObjectiveCard> getPrivateObjectCards() {
        return privateObjectCards;
    }

    public int getFavorTokens() {
        return favorTokens;
    }

    public List<ObjectiveCard> getPublicObjectCards() {
        return publicObjectCards;
    }

    public List<ToolCard> getToolCards() {
        return toolCards;
    }

    public int getTurnTimeout() {
        return turnTimeout;
    }

    public List<String> getPlayerIds() {
        return playerIds;
    }

    public void setWindowFrame(WindowPattern windowPattern) {
        windowFrame = new WindowFrame(windowPattern);
    }

    public WindowFrame getWindowFrame() {
        return windowFrame;
    }

    /* Events */
    public abstract void onLogin(boolean result);

    public abstract void onInitialSetup();

    public abstract void onDicePlaced(Position position, Dice dice);

    public abstract void onDraftPoolChanged();

    public abstract void onGameOver();

    public abstract void onInvalidAction(Action action);

    public abstract void onNewTurn();

    public abstract void onPointsChanged(int points);

    public abstract void onTokensChanged(int tokens);

    public abstract void onGameStarted();

}

package it.polimi.se2018.view;

import it.polimi.se2018.controller.actions.Action;
import it.polimi.se2018.controller.events.Event;
import it.polimi.se2018.controller.events.InitialSetupEvent;
import it.polimi.se2018.model.*;
import it.polimi.se2018.network.Client;
import it.polimi.se2018.network.ConnectionType;
import it.polimi.se2018.rmi.RmiClient;
import it.polimi.se2018.socket.SocketClient;
import it.polimi.se2018.util.Observer;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class PlayerViewBase implements Observer, PlayerView {

    private static final Logger LOGGER = Logger.getLogger("PlayerView");

    private final String playerId;
    private final Client client;
    private final PlayerView playerViewImpl;

    // Player properties
    private Player.Color playerColor;
    private List<WindowPatternCard> windowPatternCards;
    private WindowFrame windowFrame;
    private List<ObjectiveCard> privateObjectCards;
    private int favorTokens;
    private ToolCard toolCard;
    private Dice rethrownDice;
    private Dice newDice;
    private boolean isReady;
    private boolean isSuspended;

    // Game properties
    private List<ObjectiveCard> publicObjectCards;
    private List<ToolCard> toolCards;
    private int turnTimeout;
    private List<String> playerIds;
    private Map<String, WindowFrame> rivalFrames;
    private Map<String, Integer> rivalTokens;
    private List<Dice> draftPool;
    private String currentPlayerId;
    private List<Dice> trackDices;

    public PlayerViewBase(PlayerView playerViewImpl, String playerId, ConnectionType connectionType)
            throws IOException, NotBoundException {
        this.playerViewImpl = playerViewImpl;
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
        client.logout();
    }

    public String getPlayerId() {
        return playerId;
    }

    @Override
    public void handle(Event event) {
        event.update(this);
    }

    @Override
    public void send(Action action) {
        client.send(action);
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

    public Map<String, Integer> getRivalsFavorTokens() {
        return rivalTokens;
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

    public WindowFrame getWindowFrame() {
        return windowFrame;
    }

    public Map<String, WindowFrame> getRivalWindowFrames() {
        return rivalFrames;
    }

    public void setToolCard(ToolCard toolCard) {
        this.toolCard = toolCard;
    }

    public ToolCard getToolCard() {
        return toolCard;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    @Override
    public PlayerViewBase getPlayerViewBase() {
        return this;
    }

    @Override
    public void onLogin(boolean result) {
        playerViewImpl.onLogin(result);
    }

    @Override
    public void onInitialSetup(InitialSetupEvent.Data data) {
        this.playerColor = data.playerColor;
        this.windowPatternCards = data.windowPatternCards;
        this.privateObjectCards = data.privateObjectCards;

        this.publicObjectCards = data.publicObjectCards;
        this.toolCards = data.toolCards;
        this.turnTimeout = data.turnTimeout;
        this.playerIds = data.playerIds;

        playerViewImpl.onInitialSetup(data);
    }

    @Override
    public void onWindowFrameChanged(String owner, WindowFrame windowFrame) {
        if (playerId.equals(owner)) {
            this.windowFrame = windowFrame;
        } else {
            rivalFrames.put(owner, windowFrame);
        }
        playerViewImpl.onWindowFrameChanged(owner, windowFrame);
    }

    @Override
    public void onDraftPoolChanged(List<Dice> draftPool) {
        this.draftPool = draftPool;
        playerViewImpl.onDraftPoolChanged(draftPool);
    }

    @Override
    public void onGameOver(Map<String, Integer> chart) {
        playerViewImpl.onGameOver(chart);
    }

    @Override
    public void onInvalidAction(Action action) {
        playerViewImpl.onInvalidAction(action);
    }

    @Override
    public void onNewTurn(String playerId) {
        newDice = null;
        toolCard = null;
        rethrownDice = null;
        currentPlayerId = playerId;
        playerViewImpl.onNewTurn(playerId);
    }

    @Override
    public void onPointsChanged(int points) {
        playerViewImpl.onPointsChanged(points);
    }

    @Override
    public void onTokensChanged(String ownerId, int tokens) {
        if (getPlayerId().equals(ownerId)) {
            favorTokens = tokens;
        } else {
            rivalTokens.put(ownerId, tokens);
        }
        playerViewImpl.onTokensChanged(ownerId, tokens);
    }

    @Override
    public void onGameStarted(Map<String, WindowFrame> windowFrames, Map<String, Integer> tokens) {
        rivalFrames = new HashMap<>();
        for (Map.Entry<String, WindowFrame> entry : windowFrames.entrySet()) {
            if (entry.getKey().equals(getPlayerId())) {
                this.windowFrame = entry.getValue();
            } else {
                rivalFrames.put(entry.getKey(), entry.getValue());
            }
        }

        rivalTokens = new HashMap<>();
        for (Map.Entry<String, Integer> entry : tokens.entrySet()) {
            if (entry.getKey().equals(getPlayerId())) {
                this.favorTokens = entry.getValue();
            } else {
                rivalTokens.put(entry.getKey(), entry.getValue());
            }
        }

        playerViewImpl.onGameStarted(windowFrames, tokens);
    }

    @Override
    public void onNewDraftDice(Dice dice) {
        newDice = dice;
    }

    @Override
    public void onPlayerSuspended() {
        isSuspended = true;
        playerViewImpl.onPlayerSuspended();
    }

    @Override
    public void onDiceTrackChanged(List<Dice> track) {
        trackDices = track;
        playerViewImpl.onDiceTrackChanged(track);
    }

    public Dice getNewDice() {
        return newDice;
    }

    public boolean isCurrentPlayer() {
        return playerId.equals(currentPlayerId);
    }

    public List<Dice> getDraftPool() {
        return draftPool;
    }

    public void setRethrownDice(Dice dice) {
        rethrownDice = dice;
    }

    public Dice getRethrownDice() {
        return rethrownDice;
    }

    public List<Dice> getTrackDices() {
        return trackDices;
    }

    public boolean isSuspended() {
        return isSuspended;
    }
}



package it.polimi.se2018.controller;

import it.polimi.se2018.Observable;
import it.polimi.se2018.Observer;
import it.polimi.se2018.controller.actions.Action;
import it.polimi.se2018.controller.events.Event;
import it.polimi.se2018.controller.events.InvalidActionEvent;
import it.polimi.se2018.controller.events.LoginEvent;
import it.polimi.se2018.model.*;
import it.polimi.se2018.network.ClientHandler;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Controller implements Observable {

    private static final Controller INSTANCE = new Controller();

    private static final Logger LOGGER = Logger.getLogger("Controller");

    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 4;

    private static final int PATTERN_CARDS_PER_PLAYER = 2;

    private final List<String> waitingPlayers = new LinkedList<>();
    private final Map<String, Player> playersMap = new HashMap<>();
    private final Map<String, ClientHandler> clientsMap = new HashMap<>();
    private final List<Game> games = new LinkedList<>();

    public static Controller getInstance() {
        return INSTANCE;
    }

    @Override
    public void send(Event event) {
        String playerId = event.getPlayerId();
        try {
            getClient(playerId).sendNetwork(event);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Could not send event to:" + playerId, e);
        }
    }

    @Override
    public void handle(Action action) {
        Player player = getPlayer(action.getPlayerId());
        try {
            action.perform(player);
        } catch (RuntimeException e) {
            LOGGER.log(Level.SEVERE, "Invalid action", e);
            send(new InvalidActionEvent(action.getPlayerId(), action));
        }
    }


    public Player getPlayer(String id) {
        return playersMap.get(id);
    }

    public ClientHandler getClient(String id) {
        return clientsMap.get(id);
    }

    public synchronized void joinGame(String playerId, ClientHandler client) {

        try {
            if (waitingPlayers.contains(playerId)) {
                client.sendNetwork(new LoginEvent(playerId, false));
                LOGGER.log(Level.INFO, "Duplicate player ID: " + playerId);
                return;
            } else {
                client.sendNetwork(new LoginEvent(playerId, true));
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Could not send login notification, dropping client", e);
            return;
        }
        waitingPlayers.add(playerId);
        clientsMap.put(playerId, client);

        if (waitingPlayers.size() >= MAX_PLAYERS) {
            newGame();
        }
    }

    private synchronized void newGame() {
        Iterator<String> it = waitingPlayers.iterator();
        List<String> waitingClientsRemoved = new ArrayList<>();
        while (it.hasNext() && waitingClientsRemoved.size() < MAX_PLAYERS) {
            waitingClientsRemoved.add(it.next());
            it.remove();
        }

        List<WindowPatternCard> windowPatternCards = new WindowPatternsDeck().getWindowPatternCards();
        Collections.shuffle(windowPatternCards);
        List<Player> newPlayers = new ArrayList<>();
        for (int i = 0; i < waitingClientsRemoved.size(); i++) {
            Player.Color playerColor = Player.Color.values()[i];
            String playerId = waitingClientsRemoved.get(i);
            List<WindowPatternCard> playerWindowPatternCards = new ArrayList<>();
            for (int j = 0; j < PATTERN_CARDS_PER_PLAYER; j++) {
                playerWindowPatternCards.add(windowPatternCards.remove(0));
            }

            Player player = new Player(playerId, playerWindowPatternCards, playerColor);
            newPlayers.add(player);
            playersMap.put(playerId, player);
        }
        Game game = new Game(newPlayers);
        games.add(game);
    }
}
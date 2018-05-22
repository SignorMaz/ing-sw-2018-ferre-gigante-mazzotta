package it.polimi.se2018.controller;

import it.polimi.se2018.Observable;
import it.polimi.se2018.Observer;
import it.polimi.se2018.controller.actions.Action;
import it.polimi.se2018.controller.events.Event;
import it.polimi.se2018.model.Game;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.WindowPattern;
import it.polimi.se2018.model.WindowPatternsDeck;

import java.util.*;


public class Controller implements Observer, Observable {

        private static final Controller INSTANCE = new Controller();

        private static final int MIN_PLAYERS = 2;
        private static final int MAX_PLAYERS = 4;

        private final List<Client> waitingClients = new LinkedList<>();
        private final Map<String, Player> playersMap = new HashMap<>();
        private final Map<String, Client> clientsMap = new HashMap<>();
        private final List<Game> games = new LinkedList<>();
        public static Controller getInstance() {
            return INSTANCE;
        }

            @Override
    public void send(Event event) {
                    // TODO
                        }

            @Override
    public void send(Action action) {
                    action.doAction();
                }
    public Player getPlayer(String id) {
                        return playersMap.get(id);
                    }

            public Client getClient(String id) {
                        return clientsMap.get(id);
                    }

            public synchronized void joinGame(Client client) {
                        if (waitingClients.contains(client)) {
                                throw new IllegalArgumentException("Player with same ID already in queue");
                            }
                        waitingClients.add(client);

                                if (waitingClients.size() >= MAX_PLAYERS) {
                                newGame();
                            }
                    }

            private synchronized void newGame() {
                        Iterator<Client> it = waitingClients.iterator();
                        List<Client> waitingClientsRemoved = new ArrayList<>();
                        while (it.hasNext() && waitingClientsRemoved.size() < MAX_PLAYERS) {
                                waitingClientsRemoved.add(it.next());
                                it.remove();
                            }

                                List<WindowPattern> windowPatterns = new WindowPatternsDeck().getWindowPatterns();
                        Collections.shuffle(windowPatterns);
                        List<Player> newPlayers = new ArrayList<>();
                        for (int i = 0; i < waitingClientsRemoved.size(); i++) {
                                Player.Color playerColor = Player.Color.values()[i];
                                String playerId = waitingClientsRemoved.get(i).getPlayerId();
                                Player player = new Player(playerId, windowPatterns.remove(0), windowPatterns.remove(0), playerColor);
                                newPlayers.add(player);
                                playersMap.put(playerId, player);
                                clientsMap.put(playerId, waitingClientsRemoved.get(i));
                            }
                        Game game = new Game(newPlayers);
                        games.add(game);
                    }
    }
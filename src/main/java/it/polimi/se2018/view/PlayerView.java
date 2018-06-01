package it.polimi.se2018.view;

import it.polimi.se2018.Observer;
import it.polimi.se2018.controller.actions.Action;
import it.polimi.se2018.controller.events.Event;
import it.polimi.se2018.rmi.RmiClient;
import it.polimi.se2018.network.Client;
import it.polimi.se2018.network.ConnectionType;
import it.polimi.se2018.socket.SocketClient;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class PlayerView implements Observer {

    private static final Logger LOGGER = Logger.getLogger("PlayerView");
    private final String playerId;
    private final Client client;

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

    public String getPlayerId() {
        return playerId;
    }

    @Override
    public void handle(Event event) {
        event.update(this);
    }

    public void send(Action action) {
        try {
            client.send(action);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Could not send Action", e);
        }

    }

}

package it.polimi.se2018.socket;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.controller.actions.Action;
import it.polimi.se2018.controller.events.Event;
import it.polimi.se2018.network.ClientHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Server side code to handle the communication with a remote client.
 * This class forwards the Actions sent by remote clients to the Controller
 * and lets the Controller send Events to remote clients.
 */
public class SocketClientHandler implements Runnable, ClientHandler {

    private static final Logger LOGGER = Logger.getLogger("SocketClientHandler");

    private final Object lock = new Object();

    private final Socket socket;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;

    public SocketClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        inputStream = new ObjectInputStream(this.socket.getInputStream());
        outputStream = new ObjectOutputStream(this.socket.getOutputStream());

        try {
            Object object = inputStream.readObject();
            if (object instanceof String) {
                handleLogin((String) object);
            } else {
                throw new IllegalArgumentException("Not a valid ID");
            }
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.WARNING, "Could not read object", e);
        }

    }

    /**
     * Keep listening for incoming Actions from the remote client
     */
    @Override
    public void run() {
        boolean loop = true;
        while (loop && !socket.isClosed()) {
            receive();
        }
    }

    /**
     * Receive an Action object from the remote client
     */
    private void receive() {
        Object object = null;
        try {
            synchronized (lock) {
                object = inputStream.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Could not read stream", e);
        }

        if (object instanceof Action) {
            handle((Action) object);
        } else if (object != null) {
            LOGGER.severe("Received object of incorrect type");
        }
    }

    private void removeClient(String playerId) {
        Controller.getInstance().removeClient(playerId);
        try {
            socket.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Could not close the socket", e);
        }
    }

    /**
     * Pass the Action received from the remote client to the Controller
     */
    @Override
    public void handle(Action action) {
        Controller.getInstance().handle(action);
    }

    /**
     * Send the Event received from the Controller to the remote client
     */
    @Override
    public void send(Event event) {
        try {
            outputStream.writeObject(event);
        } catch (IOException e) {
            removeClient(event.getPlayerId());
            LOGGER.log(Level.SEVERE, "Removing " + event.getPlayerId(), e);
        }
    }

    /**
     * Handle the login request of a remote client
     */
    @Override
    public void handleLogin(String playerId) {
        Controller.getInstance().joinGame(playerId, this);
    }
}

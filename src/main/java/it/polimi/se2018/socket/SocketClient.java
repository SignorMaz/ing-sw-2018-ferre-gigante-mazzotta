package it.polimi.se2018.socket;

import it.polimi.se2018.util.Observer;
import it.polimi.se2018.controller.actions.Action;
import it.polimi.se2018.controller.events.Event;
import it.polimi.se2018.network.Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.polimi.se2018.socket.SocketServer.DEFAULT_PORT_SOCKET;

/**
 * Client side code to handle the communication with the server.
 * This class sends the Actions of the view to the Server
 * and forwards the events coming from the server to the view.
 */
public class SocketClient extends Thread implements Client {

    private static final Logger LOGGER = Logger.getLogger("SocketClient");

    private final Object lock = new Object();

    private final Socket socket;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;
    private final Observer observer;

    public SocketClient(Observer observer, String host, int port) throws IOException {
        this.observer = observer;
        socket = new Socket(host, port);
        outputStream = new ObjectOutputStream(this.socket.getOutputStream());
        inputStream = new ObjectInputStream(this.socket.getInputStream());
        start();
    }

    public SocketClient(Observer observer, String host) throws IOException {
        this(observer, host, DEFAULT_PORT_SOCKET);
    }

    public SocketClient(Observer observer) throws IOException {
        this(observer, "localhost");
    }

    /**
     * Keep listening for incoming Events from the server
     */
    @Override
    public void run() {
        boolean loop = true;
        while (loop && !socket.isClosed()) {
            receive();
        }
    }

    /**
     * Receive an Event object from the server
     */
    private void receive() {
        try {
            Object object;
            synchronized (lock) {
                object = inputStream.readObject();
            }
            if (object instanceof Event) {
                handle((Event) object);
            } else {
                LOGGER.severe("Received object of incorrect type");
            }
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log(Level.WARNING, "Could not read object", e);
        }
    }

    /**
     * Send an Action that the server will handle
     */
    @Override
    public void send(Action action) {
        try {
            outputStream.writeObject(action);
        } catch (IOException e) {
            // TODO: handle this error
        }
    }

    /**
     * Update the view using the event received from the server
     */
    @Override
    public void handle(Event event) {
        observer.handle(event);
    }

    /**
     * Send the ID to the server to log-in
     */
    @Override
    public void login(String playerId) throws IOException {
        outputStream.writeObject(playerId);
    }
}
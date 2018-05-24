package it.polimi.se2018.network;

import it.polimi.se2018.controller.actions.Action;
import it.polimi.se2018.controller.events.Event;

import java.io.IOException;

/**
 * This interface defines the methods that a server side client handler
 * must define to allow the communication.
 */
public interface ClientHandler {

    /**
     * Send an Event to the remote client
     *
     * @param event event to send
     * @throws IOException in case of communication errors
     */
    void sendNetwork(Event event) throws IOException;

    /**
     * Handle the action received from the remote client
     * @param action action to handle
     */
    void handle(Action action);

    /**
     * Handle the login request of the remote client
     *
     * @param playerId id of the player logging in
     */
    void handleLogin(String playerId);
}

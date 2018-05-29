package it.polimi.se2018.network;

import it.polimi.se2018.controller.actions.Action;
import it.polimi.se2018.controller.events.Event;

import java.io.IOException;

/**
 * This interface defines the methods that a client must define to
 * allow the communication.
 */
public interface Client {

    /**
     * Send an Action to the server
     *
     * @param action action to send
     * @throws IOException in case of communication errors
     */
    void sendNetwork(Action action) throws IOException;

    /**
     * Handle the event sent by the server
     *
     * @param event event to handle
     */
    void handleNetwork(Event event);

    /**
     * Send a login request to the server
     *
     * @param playerId the id of the player logging in
     */
    void login(String playerId) throws IOException;
}


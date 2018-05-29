package it.polimi.se2018.network;

import it.polimi.se2018.Observable;
import it.polimi.se2018.controller.actions.Action;
import it.polimi.se2018.controller.events.Event;

import java.io.IOException;

/**
 * This interface defines the methods that a server side client handler
 * must define to allow the communication.
 */
public interface ClientHandler extends Observable {
    /**
     * Handle the login request of the remote client
     *
     * @param playerId id of the player logging in
     */
    void handleLogin(String playerId);
}

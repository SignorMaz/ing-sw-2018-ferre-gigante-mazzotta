package it.polimi.se2018.network;

import it.polimi.se2018.util.Observer;

import java.io.IOException;

/**
 * This interface defines the methods that a client must define to
 * allow the communication.
 */
public interface Client extends Observer {

    /**
     * Send a login request to the server
     *
     * @param playerId the id of the player logging in
     */
    void login(String playerId) throws IOException;

    /**
     * Send a logout request to the server
     *
     * @param playerId the id of the player logging out
     */
    void logout(String playerId) throws IOException;
}


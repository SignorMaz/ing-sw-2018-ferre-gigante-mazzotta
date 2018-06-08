package it.polimi.se2018.rmi;

import it.polimi.se2018.util.Observer;
import it.polimi.se2018.controller.actions.Action;
import it.polimi.se2018.controller.events.Event;
import it.polimi.se2018.network.Client;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.polimi.se2018.rmi.RmiServer.DEFAULT_PORT_RMI;
import static it.polimi.se2018.rmi.RmiServer.SERVER_PATH;

public class RmiClient implements Client, RmiClientInterface {

    private static final Logger LOGGER = Logger.getLogger("RmiClient");

    private final RmiServerInterface server;
    private final RmiClientInterface client;
    private final Observer observer;
    private String playerId;

    public RmiClient(Observer observer, String host, int port) throws RemoteException, MalformedURLException, NotBoundException {
        this.observer = observer;
        String url = "//" + host + ":" + port + "/" + SERVER_PATH;
        server = (RmiServerInterface) Naming.lookup(url);
        client = (RmiClientInterface) UnicastRemoteObject.exportObject(this, 0);
    }

    public RmiClient(Observer observer, String host) throws RemoteException, MalformedURLException, NotBoundException {
        this(observer, host, DEFAULT_PORT_RMI);
    }

    public RmiClient(Observer observer) throws RemoteException, NotBoundException, MalformedURLException {
        this(observer, "localhost");
    }

    @Override
    public void send(Action action) {
        try {
            server.handleRmi(this, action);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, "Could not send action", e);
            try {
                logout();
            } catch (IOException e1) {
                LOGGER.log(Level.SEVERE, "Could not log out", e1);
            }
        }
    }

    @Override
    public void handle(Event event) {
        observer.handle(event);
    }

    @Override
    public void login(String playerId) throws IOException {
        server.handleLoginRmi(playerId, client);
        this.playerId = playerId;
    }

    @Override
    public void logout() throws IOException {
        server.handleLogoutRmi(playerId);
    }

    @Override
    public void handleRmi(Event event) throws RemoteException {
        handle(event);
    }
}
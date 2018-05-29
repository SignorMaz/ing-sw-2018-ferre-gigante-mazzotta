package it.polimi.se2018.rmi;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.controller.actions.Action;
import it.polimi.se2018.controller.events.Event;
import it.polimi.se2018.network.ClientHandler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class RmiServer implements ClientHandler, RmiServerInterface {

    public static final int DEFAULT_PORT_RMI = 1099;
    public static final String SERVER_PATH = "Server";

    private Map<String, RmiClientInterface> clients = new HashMap<>();

    private RmiServer() {
    }

    public static void createAndListen(int port) throws RemoteException, MalformedURLException {
        RmiServer server = new RmiServer();
        UnicastRemoteObject.exportObject(server, 0);
        LocateRegistry.createRegistry(port);
        Naming.rebind(SERVER_PATH, server);
    }

    public static void createAndListen() throws RemoteException, MalformedURLException {
        createAndListen(DEFAULT_PORT_RMI);
    }

    @Override
    public void handle(Action action) {
        Controller.getInstance().handle(action);
    }

    @Override
    public void send(Event event) throws IOException {
        clients.get(event.getPlayerId()).handleRmi(event);
    }

    @Override
    public void handleLogin(String playerId) {
        Controller.getInstance().joinGame(playerId, this);
    }

    @Override
    public void handleRmi(Action action) throws RemoteException {
        handle(action);
    }

    @Override
    public void handleLoginRmi(String playerId, RmiClientInterface rmiClientInterface) throws RemoteException {
        clients.put(playerId, rmiClientInterface);
        handleLogin(playerId);
    }
}
package it.polimi.se2018.rmi;

import it.polimi.se2018.controller.actions.Action;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiServerInterface extends Remote {
    void handleRmi(Action action) throws RemoteException;
    void handleLoginRmi(String playerId, RmiClientInterface rmiClientInterface) throws RemoteException;
    void handleLogoutRmi(String playerId) throws RemoteException;
}
package it.polimi.se2018.rmi;

import it.polimi.se2018.controller.events.Event;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiClientInterface extends Remote {
    void handleRmi(Event event) throws RemoteException;
}
package it.polimi.se2018.network;

import it.polimi.se2018.controller.actions.Action;
import it.polimi.se2018.controller.events.Event;
import it.polimi.se2018.controller.events.LoginEvent;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.view.PlayerView;

import java.io.IOException;
import java.rmi.NotBoundException;

class PlayerViewLoginTest extends PlayerView {

    private boolean loginEventReceived = false;

    PlayerViewLoginTest(String playerId, ConnectionType connectionType) throws IOException, NotBoundException {
        super(playerId, connectionType);
    }

    @Override
    public void handle(Event event) {
        synchronized (this) {
            loginEventReceived = event instanceof LoginEvent;
            notifyAll();
        }
    }

    boolean getLoginEventReceived() {
        return loginEventReceived;
    }

    synchronized void waitForEvent() {
        try {
            while (!loginEventReceived) {
                wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

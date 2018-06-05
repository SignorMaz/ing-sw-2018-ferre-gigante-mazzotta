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
    private boolean loginResult = false;

    PlayerViewLoginTest(String playerId, ConnectionType connectionType) throws IOException, NotBoundException {
        super(playerId, connectionType);
    }

    @Override
    public void onLogin(boolean result) {
        synchronized (this) {
            loginEventReceived = true;
            loginResult = result;
            notifyAll();
        }
    }

    @Override
    public void onInitialSetup() {

    }

    @Override
    public void onDicePlaced(Position position, Dice dice) {

    }

    @Override
    public void onDraftPoolChanged() {

    }

    @Override
    public void onGameOver() {

    }

    @Override
    public void onInvalidAction(Action action) {

    }

    @Override
    public void onNewTurn() {

    }

    @Override
    public void onPointsChanged(int points) {

    }

    @Override
    public void onTokensChanged(int tokens) {

    }

    @Override
    public void onGameStarted() {

    }

    boolean getLoginResult() {
        return loginResult;
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

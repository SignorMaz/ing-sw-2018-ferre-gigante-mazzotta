package it.polimi.se2018.network;

import it.polimi.se2018.controller.events.Event;
import it.polimi.se2018.controller.events.LoginEvent;
import it.polimi.se2018.rmi.RmiServer;
import it.polimi.se2018.socket.SocketServer;
import it.polimi.se2018.view.PlayerView;
import org.junit.Test;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

public class ServerTest {
    private static final Logger LOGGER = Logger.getLogger("ServerTest");

    /**
     * Simple PlayerView implementation to test the login functionality
     */
    private static class PlayerViewImpl extends PlayerView {
        private boolean loginEventReceived = false;

        private PlayerViewImpl(String playerId, ConnectionType connectionType) throws IOException, NotBoundException {
            super(playerId, connectionType);
        }

        @Override
        public void handle(Event event) {
            synchronized (this) {
                loginEventReceived = event instanceof LoginEvent;
                notifyAll();
            }
        }

        private synchronized void waitForEvent() {
            try {
                while (!loginEventReceived) {
                    wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testLoginSocket() {
        Runnable serverRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    SocketServer.createAndListen();
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, "Could not start socket server", e);
                    fail();
                }
            }
        };
        Thread serverThread = new Thread(serverRunnable);
        serverThread.start();

        // It takes a bit of time for the server to be ready
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            PlayerViewImpl playerView = new PlayerViewImpl("My Socket player", ConnectionType.SOCKET);
            playerView.login();
            playerView.waitForEvent();
            assertTrue(playerView.loginEventReceived);
        } catch (IOException | NotBoundException e) {
            LOGGER.log(Level.SEVERE, "Could not create PlayerView", e);
            fail();
        }
    }


    @Test
    public void testLoginRmi() {
        Runnable serverRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    RmiServer.createAndListen();
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, "Could not start RMI server", e);
                    fail();
                }
            }
        };
        Thread serverThread = new Thread(serverRunnable);
        serverThread.start();

        // It takes a bit of time for the server to be ready
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            PlayerViewImpl playerView = new PlayerViewImpl("My RMI player ID", ConnectionType.RMI);
            playerView.login();
            playerView.waitForEvent();
            assertTrue(playerView.loginEventReceived);
        } catch (IOException | NotBoundException e) {
            LOGGER.log(Level.SEVERE, "Could not create PlayerView", e);
            fail();
        }
    }
}

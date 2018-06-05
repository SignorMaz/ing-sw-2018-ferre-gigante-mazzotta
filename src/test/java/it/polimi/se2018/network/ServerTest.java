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
            PlayerViewLoginTest playerView = new PlayerViewLoginTest("My Socket player", ConnectionType.SOCKET);
            playerView.login();
            playerView.waitForEvent();
            assertTrue(playerView.getLoginEventReceived());
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
            PlayerViewLoginTest playerView = new PlayerViewLoginTest("My RMI player ID", ConnectionType.RMI);
            playerView.login();
            playerView.waitForEvent();
            assertTrue(playerView.getLoginEventReceived());
        } catch (IOException | NotBoundException e) {
            LOGGER.log(Level.SEVERE, "Could not create PlayerView", e);
            fail();
        }
    }
}

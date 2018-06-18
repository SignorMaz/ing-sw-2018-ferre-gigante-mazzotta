/*package it.polimi.se2018.network;

import it.polimi.se2018.rmi.RmiServer;
import it.polimi.se2018.socket.SocketServer;
import org.junit.Test;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static junit.framework.TestCase.*;

public class ServerTest {
    private static final Logger LOGGER = Logger.getLogger("ServerTest");

    static {
        Runnable socketRunnable = new Runnable() {
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
        Thread socketThread = new Thread(socketRunnable);
        socketThread.start();

        Runnable rmiRunnable = new Runnable() {
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
        Thread rmiThread = new Thread(rmiRunnable);
        rmiThread.start();

        // It takes a bit of time for the server to be ready
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void loginSuccess(String playerId, ConnectionType type) {
        try {
            PlayerViewLoginTest playerView = new PlayerViewLoginTest(playerId, type);
            playerView.getPlayerViewBase().login();
            playerView.waitForEvent();
            assertTrue(playerView.getLoginResult());
        } catch (IOException | NotBoundException e) {
            LOGGER.log(Level.SEVERE, "Could not create PlayerView", e);
            fail();
        }
    }

    private void loginFail(String playerId, ConnectionType type) {
        try {
            PlayerViewLoginTest playerView = new PlayerViewLoginTest(playerId, type);
            playerView.getPlayerViewBase().login();
            playerView.waitForEvent();
            assertFalse(playerView.getLoginResult());
        } catch (IOException | NotBoundException e) {
            LOGGER.log(Level.SEVERE, "Could not create PlayerView", e);
            fail();
        }
    }

    @Test
    public void testLoginSocket() {
        loginSuccess("My Socket Player", ConnectionType.SOCKET);
    }

    @Test
    public void testLoginRmi() {
        loginSuccess("My RMI Player", ConnectionType.RMI);
    }

    @Test
    public void testDuplicateIdLoginRmi() {
        loginSuccess("My duplicate RMI Player", ConnectionType.RMI);
        loginFail("My duplicate RMI Player", ConnectionType.RMI);
        loginFail("My duplicate RMI Player", ConnectionType.RMI);
    }

    @Test
    public void testDuplicateIdLoginSocket() {
        loginSuccess("My duplicate Socket Player", ConnectionType.SOCKET);
        loginFail("My duplicate Socket Player", ConnectionType.SOCKET);
        loginFail("My duplicate Socket Player", ConnectionType.SOCKET);
    }

    @Test
    public void testDuplicateIdLoginRMISocket() {
        loginSuccess("My duplicate RMI/Socket Player", ConnectionType.RMI);
        loginFail("My duplicate RMI/Socket Player", ConnectionType.SOCKET);
        loginFail("My duplicate RMI/Socket Player", ConnectionType.RMI);
        loginFail("My duplicate RMI/Socket Player", ConnectionType.SOCKET);
    }

    @Test
    public void testDuplicateIdLoginSocketRMI() {
        loginSuccess("My duplicate Socket/RMI Player", ConnectionType.SOCKET);
        loginFail("My duplicate Socket/RMI Player", ConnectionType.RMI);
        loginFail("My duplicate Socket/RMI Player", ConnectionType.SOCKET);
        loginFail("My duplicate Socket/RMI Player", ConnectionType.RMI);
    }
}
*/
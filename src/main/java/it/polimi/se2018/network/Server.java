package it.polimi.se2018.network;

import it.polimi.se2018.socket.SocketServer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    private static final Logger LOGGER = Logger.getLogger("Server");

    private Server() {
    }

    public static void main(String[] argv) {
        LOGGER.info("Starting socket server");
        Runnable socketServer = new Runnable() {
            @Override
            public void run() {
                try {
                    SocketServer.createAndListen();
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, "Could not start socket server", e);
                }
            }
        };
        Thread socketThread = new Thread(socketServer);
        socketThread.start();

        try {
            socketThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
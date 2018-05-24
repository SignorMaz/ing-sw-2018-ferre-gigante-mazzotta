package it.polimi.se2018.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class SocketServer {

    public static final int DEFAULT_PORT_SOCKET = 9876;

    private ServerSocket serverSocket;

    private List<SocketClientHandler> clients = new LinkedList<>();

    private SocketServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public static void createAndListen(int port) throws IOException {
        SocketServer server = new SocketServer(port);
        server.startListening();
    }

    public static void createAndListen() throws IOException {
        createAndListen(DEFAULT_PORT_SOCKET);
    }

    private void startListening() {
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                SocketClientHandler socketClientHandler = new SocketClientHandler(socket);
                clients.add(socketClientHandler);
            }
        } catch (IOException e) {
            throw new AssertionError("I/O error occured, please verify environment config", e);
        }
    }
}


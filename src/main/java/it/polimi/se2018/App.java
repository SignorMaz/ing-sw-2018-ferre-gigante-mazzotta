import it.polimi.se2018.rmi.RmiServer;
import it.polimi.se2018.network.Server;
import it.polimi.se2018.socket.SocketServer;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    private static final Logger LOGGER = Logger.getLogger("App");

    private static final String SERVER_ARG = "server";
    private static final String CLIENT_ARG = "client";
    private static final String SOCKET_PORT_ARG = "--socket-port=";
    private static final String RMI_PORT_ARG = "--rmi-port=";

    private static void printHelpAndExit() {
        System.out.println(String.format("java -jar app.jar %s|%s [EXTRA_ARGS]", SERVER_ARG, CLIENT_ARG));
        System.out.println();
        System.out.println("EXTRA_ARGS are the optional arguments passed to the server or client class.");
        System.out.println("Pass --help as EXTRA_ARGS to see the options available.");
        System.exit(1);
    }

    private static void printServerHelpAndExit() {
        System.out.println(String.format("java -jar app.jar %s [%sPORT] [%sPORT] [--help]",
                SERVER_ARG, SOCKET_PORT_ARG, RMI_PORT_ARG));
        System.exit(1);
    }

    private static void printClientHelpAndExit() {
        System.out.println(String.format("java -jar app.jar %s [--help]",
                CLIENT_ARG));
        System.exit(1);
    }

    private static void handleServer(String[] args) throws InterruptedException {
        int socketPort = SocketServer.DEFAULT_PORT_SOCKET;
        int rmiPort = RmiServer.DEFAULT_PORT_RMI;
        try {
            for (String arg : args) {
                if (arg.startsWith(SOCKET_PORT_ARG)) {
                    String portNumString = arg.substring(SOCKET_PORT_ARG.length());
                    socketPort = Integer.parseInt(portNumString);
                } else if (arg.startsWith(RMI_PORT_ARG)) {
                    String portNumString = arg.substring(RMI_PORT_ARG.length());
                    rmiPort = Integer.parseInt(portNumString);
                } else {
                    printServerHelpAndExit();
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Could not parse port");
            LOGGER.log(Level.SEVERE, "Could not start server", e);
            printServerHelpAndExit();
        }

        Server.startServers(socketPort, rmiPort);
    }

    private static void handleClient(String[] args) {
        printClientHelpAndExit();
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length < 1) {
            printHelpAndExit();
        }

        // Remove the first argument to simplify the code
        String[] subArgs = Arrays.copyOfRange(args, 1, args.length);

        if (SERVER_ARG.equals(args[0])) {
            handleServer(subArgs);
        } else if (CLIENT_ARG.equals(args[0])) {
            handleClient(subArgs);
        } else {
            printHelpAndExit();
        }
    }
}
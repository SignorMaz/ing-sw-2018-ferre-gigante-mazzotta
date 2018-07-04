package it.polimi.se2018;

import it.polimi.se2018.model.Game;
import it.polimi.se2018.rmi.RmiServer;
import it.polimi.se2018.network.Server;
import it.polimi.se2018.socket.SocketServer;
import it.polimi.se2018.view.cli.PlayerViewCli;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import joptsimple.OptionSpecBuilder;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.Arrays;

public class App {

    private static final String SERVER_ARG = "server";
    private static final String CLIENT_GUI_ARG = "client-gui";
    private static final String CLIENT_CLI_ARG = "client-cli";

    private static void printHelpAndExit() {
        System.out.println("Argument       Description");
        System.out.println("--------       -----------");
        System.out.println("server         Start a server instance");
        System.out.println("client-cli     Start a CLI client");
        System.out.println("client-gui     Start a GUI client");
        System.out.println();
        System.out.println("Pass --help after the argument to see its help");
        System.exit(0);
    }

    private static void handleServer(String[] args) throws IOException, InterruptedException {
        OptionParser parser = new OptionParser();
        OptionSpec<Integer> socketPortOption = parser.accepts("socket-port", "Port for socket connections")
                .withRequiredArg().ofType(Integer.class);
        OptionSpec<Integer> rmiPortOption = parser.accepts("rmi-port", "Port for RMI connections")
                .withRequiredArg().ofType(Integer.class);
        OptionSpec<Integer> timeoutOption = parser.accepts("turn-timeout", "Turn timeout")
                .withRequiredArg().ofType(Integer.class);
        OptionSpecBuilder helpOption = parser.accepts("help");
        helpOption.forHelp();
        OptionSet options = parser.parse(args);

        if (options.has(helpOption)) {
            parser.printHelpOn(System.out);
            System.exit(0);
        }

        int socketPort = SocketServer.DEFAULT_PORT_SOCKET;
        int rmiPort = RmiServer.DEFAULT_PORT_RMI;
        if (options.has(socketPortOption)) {
            socketPort = options.valueOf(socketPortOption);
        }
        if (options.has(rmiPortOption)) {
            rmiPort = options.valueOf(rmiPortOption);
        }

        if (options.has(timeoutOption)) {
            Game.setGameTurnTimeout(options.valueOf(timeoutOption));
        }

        Server.startServers(socketPort, rmiPort);
    }

    private static void handleGuiClient(String[] args) throws IOException {
        OptionParser parser = new OptionParser();
        OptionSpecBuilder helpOption = parser.accepts("help");
        helpOption.forHelp();
        OptionSet options = parser.parse(args);

        if (options.has(helpOption)) {
            parser.printHelpOn(System.out);
            System.exit(0);
        }
    }

    private static void handleCliClient(String[] args) throws IOException, NotBoundException, InterruptedException {
        OptionParser parser = new OptionParser();
        OptionSpecBuilder helpOption = parser.accepts("help");
        helpOption.forHelp();
        OptionSet options = parser.parse(args);

        if (options.has(helpOption)) {
            parser.printHelpOn(System.out);
            System.exit(0);
        }

        PlayerViewCli.main(args);
    }

    public static void main(String[] args) throws IOException, InterruptedException, NotBoundException {

        if (args.length < 1) {
            printHelpAndExit();
        }

        // Remove the first argument and pass only the command specific arguments
        String[] subArgs = Arrays.copyOfRange(args, 1, args.length);

        if (SERVER_ARG.equals(args[0])) {
            handleServer(subArgs);
        } else if (CLIENT_GUI_ARG.equals(args[0])) {
            handleGuiClient(subArgs);
        } else if (CLIENT_CLI_ARG.equals(args[0])) {
            handleCliClient(subArgs);
        } else {
            printHelpAndExit();
        }
    }
}

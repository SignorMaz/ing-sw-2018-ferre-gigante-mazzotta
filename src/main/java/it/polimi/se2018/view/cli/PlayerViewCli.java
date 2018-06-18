package it.polimi.se2018.view.cli;

import it.polimi.se2018.controller.actions.Action;
import it.polimi.se2018.controller.events.InitialSetupEvent;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.model.WindowPattern;
import it.polimi.se2018.network.ConnectionType;
import it.polimi.se2018.view.PlayerView;
import it.polimi.se2018.view.PlayerViewBase;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class PlayerViewCli implements PlayerView {

    private final PlayerViewBase playerViewBase;

    public PlayerViewCli(String playerId, ConnectionType connectionType) throws IOException, NotBoundException {
        playerViewBase = new PlayerViewBase(this, playerId, connectionType);
    }

    @Override
    public PlayerViewBase getPlayerViewBase() {
        return playerViewBase;
    }

    @Override
    public void onLogin(boolean result) {

    }

    @Override
    public void onInitialSetup(InitialSetupEvent.Data data) {

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
    public void onGameStarted(Map<String, WindowPattern> windowPatternMap) {

    }

    public static void main(String[] args) throws IOException, NotBoundException {
        System.out.println("Choose a connection type:");
        System.out.println(" 1) RMI");
        System.out.println(" 2) Socket");

        Scanner input = new Scanner(System.in);
        ConnectionType type;
        for (;;) {
            System.out.print("Insert position (1-2): ");
            if (!input.hasNextInt()) {
                input.next();
                continue;
            }
            int connectionNum = input.nextInt();
            if (connectionNum == 1) {
                type = ConnectionType.RMI;
                break;
            } else if (connectionNum == 2) {
                type = ConnectionType.SOCKET;
                break;
            }
        }
        System.out.print("Choose a nickname: ");
        String playerId = input.next();
        PlayerView view = new PlayerViewCli(playerId, type);
        System.out.println("Connecting...");
        view.getPlayerViewBase().login();
    }
}
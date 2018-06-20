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
import java.util.*;

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
    public void onDicePlaced(String playerPlacingDice, Position position, Dice dice) {

    }

    @Override
    public void onDraftPoolChanged(List<Dice> draftPool) {
        
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
        Scanner input = new Scanner(System.in);

        List<ConnectionType> types = Arrays.asList(ConnectionType.values());
        int typePosition = InputHelper.chooseOption(input, types, null);
        ConnectionType type = types.get(typePosition);

        System.out.print("Choose a nickname: ");
        String playerId = input.next();

        PlayerView view = new PlayerViewCli(playerId, type);
        System.out.println("Connecting...");
        view.getPlayerViewBase().login();
    }
}

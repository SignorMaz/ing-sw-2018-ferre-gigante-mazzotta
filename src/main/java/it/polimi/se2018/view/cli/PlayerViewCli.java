package it.polimi.se2018.view.cli;

import it.polimi.se2018.controller.actions.Action;
import it.polimi.se2018.controller.events.InitialSetupEvent;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.model.WindowPattern;
import it.polimi.se2018.network.ConnectionType;
import it.polimi.se2018.view.PlayerView;
import it.polimi.se2018.view.PlayerViewBase;
import it.polimi.se2018.view.cli.commands.Command;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.*;

public class PlayerViewCli implements PlayerView {

    private final PlayerViewBase playerViewBase;
    private final Scanner scanner;
    private boolean isGameOver = false;
    private boolean isGameStarted = false;
    private boolean isGameSetUp = false;
    private boolean isMyTurn = false;

    public PlayerViewCli(Scanner scanner, String playerId, ConnectionType connectionType)
            throws IOException, NotBoundException {
        playerViewBase = new PlayerViewBase(this, playerId, connectionType);
        this.scanner = scanner;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public boolean isMyTurn() {
        return isMyTurn;
    }

    public boolean isGameSetUp() {
        return isGameSetUp;
    }

    public boolean isGameStarted() {
        return isGameStarted;
    }

    @Override
    public PlayerViewBase getPlayerViewBase() {
        return playerViewBase;
    }

    @Override
    public void onLogin(boolean result) {
        System.out.println("You logged in");
    }

    @Override
    public void onInitialSetup(InitialSetupEvent.Data data) {
        synchronized (this) {
            isGameSetUp = true;
            notify();
        }
    }

    @Override
    public void onDicePlaced(String playerPlacingDice, Position position, Dice dice) {
        if (playerPlacingDice.equals(getPlayerViewBase().getPlayerId())) {
            System.out.println("You successfully placed the dice");
        } else {
            System.out.println(playerPlacingDice + "'s placed a dice");
        }
    }

    @Override
    public void onDraftPoolChanged(List<Dice> draftPool) {
        System.out.println("The draft pool changed");
    }

    @Override
    public void onGameOver() {
        System.out.println("The game is over");
        isGameOver = true;
    }

    @Override
    public void onInvalidAction(Action action) {
        System.out.println("Invalid move");
    }

    @Override
    public void onNewTurn(String playerId) {
        isGameStarted = true;
        if (getPlayerViewBase().getPlayerId().equals(playerId)) {
            System.out.println("It's your turn");
            isMyTurn = true;
        } else {
            System.out.println("It's " + playerId + "'s turn!");
            isMyTurn = false;
        }
    }

    @Override
    public void onPointsChanged(int points) {
    }

    @Override
    public void onTokensChanged(int tokens) {
        System.out.println("Your new tokens count is " + getPlayerViewBase().getFavorTokens());
    }

    @Override
    public void onGameStarted(Map<String, WindowPattern> windowPatternMap) {
        System.out.println("Game started!");
    }

    private void handleUserInput() {
        List<Command> options = new ArrayList<>();
        for (Command command : CommandList.COMMANDS) {
            if (!command.canPerform(this)) {
                continue;
            }
            options.add(command);
        }
        int optionNum = InputHelper.chooseOption(scanner, options, Command::getText, true);
        if (optionNum >= options.size()) {
            return;
        }
        options.get(optionNum).handle(this);
    }

    public void looper() {
        while (!isGameOver) {
            try {
                synchronized (this) {
                    while (!isGameSetUp) {
                        wait();
                    }
                }
            } catch (InterruptedException e) {
                continue;
            }
            System.out.println();
            handleUserInput();
        }
    }

    public static void main(String[] args) throws IOException, NotBoundException {
        System.out.println("Choose a connection type:");
        Scanner input = new Scanner(System.in);

        List<ConnectionType> types = Arrays.asList(ConnectionType.values());
        int typePosition = InputHelper.chooseOption(input, types, null, false);
        ConnectionType type = types.get(typePosition);

        System.out.print("Choose a nickname: ");
        String playerId = input.next();

        PlayerViewCli view = new PlayerViewCli(input, playerId, type);
        System.out.println("Connecting...");
        try {
            view.getPlayerViewBase().login();
        } catch (IOException exception) {
            System.out.println("Could not login. Please try again witha different username");
        }

        view.looper();
    }
}
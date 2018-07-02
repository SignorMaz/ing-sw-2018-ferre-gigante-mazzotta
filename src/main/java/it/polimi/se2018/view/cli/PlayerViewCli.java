package it.polimi.se2018.view.cli;

import it.polimi.se2018.controller.actions.Action;
import it.polimi.se2018.controller.events.InitialSetupEvent;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.model.WindowFrame;
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
        if (result) {
            System.out.println("Logged in, waiting...");
        } else {
            System.out.println("Could not login, try using a different username");
            System.exit(1);
        }
    }

    @Override
    public void onInitialSetup(InitialSetupEvent.Data data) {
        synchronized (this) {
            isGameSetUp = true;
            notifyAll();
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
    public void onGameOver(Map<String, Integer> chart) {
        System.out.println("The game is over");
        for (Map.Entry<String, Integer> entry : chart.entrySet()) {
            System.out.println("Player: " + entry.getKey() + " - points: " + entry.getValue());
        }
        isGameOver = true;
    }

    @Override
    public void onInvalidAction(Action action) {
        System.out.println("Invalid move");
    }

    @Override
    public void onNewTurn(String playerId) {
        if (getPlayerViewBase().getPlayerId().equals(playerId)) {
            System.out.println("It's your turn");
            isMyTurn = true;
        } else {
            System.out.println("It's " + playerId + "'s turn!");
            isMyTurn = false;
        }

        System.out.println("Press enter to see the commands available");
    }

    @Override
    public void onPointsChanged(int points) {
    }

    @Override
    public void onTokensChanged(int tokens) {
        System.out.println("Your new tokens count is " + getPlayerViewBase().getFavorTokens());
    }

    @Override
    public void onGameStarted(Map<String, WindowFrame> windowPatternMap) {
        isGameStarted = true;
        System.out.println("Game started!");
    }

    @Override
    public void onNewDraftDice(Dice dice) {
        System.out.println("New dice in the draft pool: " + dice.getColor() + "-" + dice.getNumber());
    }

    @Override
    public void onPlayerSuspended() {
        System.out.println("You've been suspended");
    }

    @Override
    public void onDiceTrackChanged(List<Dice> track) {
        System.out.println("The dice track changed");
    }

    private void handleUserInput() {
        List<Command> options = new ArrayList<>();
        for (Command command : CommandList.COMMANDS) {
            if (!command.canPerform(this)) {
                continue;
            }
            options.add(command);
        }
        InputResponse<Integer> response = InputHelper.chooseOption(scanner, options, Command::getText);
        if (!response.isValid()) {
            return;
        }
        options.get(response.getValue()).handle(this);
    }

    public void looper() throws InterruptedException {
        while (!isGameOver) {

            synchronized (this) {
                while (!isGameSetUp) {
                    wait();
                }
            }

            System.out.println();
            handleUserInput();
        }
    }

    public static void main(String[] args) throws IOException, NotBoundException, InterruptedException {
        System.out.println("Choose a connection type:");
        Scanner input = new Scanner(System.in);

        List<ConnectionType> types = Arrays.asList(ConnectionType.values());
        InputResponse<Integer> response = InputHelper.chooseOption(input, types, null);
        if (!response.isValid()) {
            System.out.println("The option is not valid");
            return;
        }
        ConnectionType type = types.get(response.getValue());

        System.out.print("Choose a nickname: ");
        String playerId = input.next();
        input.nextLine(); // Consume new line character

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
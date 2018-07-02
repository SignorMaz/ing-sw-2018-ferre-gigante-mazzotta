package it.polimi.se2018.model;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.controller.events.*;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Game {

    private static final Logger LOGGER = Logger.getLogger("Game");

    private static final int TOTAL_ROUND_NUM = 10;
    private static final int PUBLIC_OBJECTIVE_CARDS_NUM = 3;
    private static final int TOOL_CARDS_NUM = 3;
    private static final int TOKENS_PER_FIRST_CARD_USE = 1;
    private static final int TOKENS_PER_CARD_USE = 2;
    private static int turnTimeoutSeconds = 150;

    private final List<Dice> roundTrackDices;
    private final List<Player> players;
    private int currentPlayerNum = 0;
    private boolean isFirstTurn = false;
    private int completedRounds = 0;
    private List<Dice> draftPool;
    private final DiceBag diceBag;
    private final List<ObjectiveCard> publicObjectiveCards;
    private final Map<ToolCard, Integer> toolCards;
    private ToolCard toolCardInUse = null;
    private boolean toolCardUsed = false;
    private boolean moveDone = false;
    private Dice rethrownDice;
    private final List<Player> skipTurnPlayerList;
    private Dice newDice;
    private boolean turnCompleted;
    private boolean gameOver;

    private final ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture scheduledTurnTimer;
    private final Runnable turnTimeOutRunnable;
    private final Set<Player> suspendedPlayers;

    public Game(List<Player> players) {
        this(players, new ToolCardsDeck().getCards());
    }

    /**
     * create all the classes for the game
     *
     * @param players list of players
     * @param toolCards list with all the ToolCards from which we will take three
     */
    public Game(List<Player> players, List<ToolCard> toolCards) {
        for (Player player : players) {
            player.setGame(this);
        }

        this.players = players;

        diceBag = new DiceBag();

        publicObjectiveCards = new ArrayList<>();
        List<ObjectiveCard> publicObjectiveCardsDeck = new PublicObjectiveCardsDeck().getCards();
        Collections.shuffle(publicObjectiveCardsDeck);
        for (int i = 0; i < PUBLIC_OBJECTIVE_CARDS_NUM; i++) {
            publicObjectiveCards.add(publicObjectiveCardsDeck.remove(0));
        }

        List<ObjectiveCard> privateObjectiveCardsDeck = new PublicObjectiveCardsDeck().getCards();
        Collections.shuffle(privateObjectiveCardsDeck);
        for (Player player : players) {
            List<ObjectiveCard> privateObjectiveCards = new ArrayList<>();
            privateObjectiveCards.add(privateObjectiveCardsDeck.remove(0));
            player.setPrivateObjectiveCards(privateObjectiveCards);
        }

        this.toolCards = new HashMap<>();
        Collections.shuffle(toolCards);
        for (int i = 0; i < TOOL_CARDS_NUM; i++) {
            this.toolCards.put(toolCards.remove(0), 0);
        }

        roundTrackDices = new ArrayList<>();
        skipTurnPlayerList = new ArrayList<>();

        turnTimeOutRunnable = new Runnable() {
            @Override
            public void run() {
                synchronized (Game.this) {
                    if (!turnCompleted) {
                        suspendPlayer(getCurrentPlayer());
                    } else {
                        nextTurn();
                    }
                }
            }
        };
        suspendedPlayers = new HashSet<>();
        notifyGameCreation();
    }

    private void notifyGameCreation() {
        List<String> playerIds = new ArrayList<>();
        for (Player player : players) {
            playerIds.add(player.getPlayerId());
        }
        for (Player player : players) {
            InitialSetupEvent.Data data = new InitialSetupEvent.Data(
                    player.getPlayerColor(),
                    player.getWindowPatternCards(),
                    player.getPrivateObjectiveCards(),
                    player.getFavorTokensCount(),
                    publicObjectiveCards,
                    getToolCards(),
                    turnTimeoutSeconds,
                    playerIds);
            Event initialSetupEvent = new InitialSetupEvent(player.getPlayerId(), data);
            Controller.getInstance().send(initialSetupEvent);
        }

        // Give turnTimeoutSeconds to the players to choose their frame
        Runnable initTimeoutRunnable = new Runnable() {
            @Override
            public void run() {
                for (Player player : players) {
                    if (!player.isReady()) {
                        suspendedPlayers.add(player);
                        Controller.getInstance().send(new PlayerSuspendedEvent(player.getPlayerId()));
                    }
                }
                tryStart();
            }
        };
        scheduledTurnTimer = scheduledExecutor.schedule(initTimeoutRunnable, turnTimeoutSeconds, TimeUnit.SECONDS);
    }

    /**
     * Called whenever a Player is ready. If all the players
     * are ready, the game will start.
     * <p>
     * Suspend the Players not ready after a certain amount of time
     */
    void tryStart() {
        for (Player player : players) {
            if (!player.isReady() && !suspendedPlayers.contains(player)) {
                return;
            }
        }

        for (Player player : players) {
            if (suspendedPlayers.contains(player)) {
                currentPlayerNum++;
            } else {
                break;
            }
        }

        newRound();
    }

    public static void setGameTurnTimeout(int timeout) {
        turnTimeoutSeconds = timeout;
    }

    /**
     * return the total round counter
     *
     * @return total rounds completed
     */
    public boolean isGameOver() {
        return completedRounds == TOTAL_ROUND_NUM || gameOver;
    }

    public void endGame() {
        notifyGameOver();
        gameOver = true;
    }

    private void notifyGameOver() {
        for (Player player : players) {
            Controller.getInstance().send(new GameOverEvent(player.getPlayerId()));
        }
    }

    /**
     * switch the game to a new round and call the methods to draw a dice
     */
    private void newRound() {
        if (isGameOver()) {
            notifyGameOver();
            return;
        }

        // We have no dices during the first round
        if (completedRounds != 0) {
            roundTrackDices.add(draftPool.remove(0));
        }

        draftPool = new ArrayList<>();
        for (int i = 0; i < players.size() + 1; i++) {
            draftPool.add(diceBag.drawDice());
        }

        // Send to each player the frame of the rivals
        for (Player player : players) {
            Map<String, WindowFrame> rivalsFrames = new HashMap<>();
            for (Player rival : players) {
                if (rival.equals(player)) {
                    continue;
                }
                rivalsFrames.put(rival.getPlayerId(), rival.getWindowFrame());
            }
            Event gameStartEvent = new GameStartEvent(player.getPlayerId(), rivalsFrames);
            Controller.getInstance().send(gameStartEvent);
        }

        startTurnAndNotify();
    }

    public synchronized void completeTurn() {
        turnCompleted = true;
        nextTurn();
    }

    /**
     * reset card for the new player, and switch the player to the next
     */
    public synchronized void startTurnTimer() {
        if (scheduledTurnTimer != null && !scheduledTurnTimer.isDone()) {
            scheduledTurnTimer.cancel(true);
        }
        scheduledTurnTimer = scheduledExecutor.schedule(turnTimeOutRunnable, turnTimeoutSeconds, TimeUnit.SECONDS);
    }

    private void startTurnAndNotify() {
        startTurnTimer();

        String currentPlayerId = getCurrentPlayer().getPlayerId();
        for (Player player : players) {
            Event newTurn = new NewTurnEvent(player.getPlayerId(), currentPlayerId);
            Controller.getInstance().send(newTurn);
        }
    }

    public synchronized void nextTurn() {
        if (isGameOver()) {
            notifyGameOver();
            return;
        }

        turnCompleted = false;

        // Reset card for the new player
        toolCardInUse = null;
        toolCardUsed = false;
        // and the move flag
        moveDone = false;
        // and the rethrown dice
        rethrownDice = null;
        newDice = null;

        if (!isFirstTurn && currentPlayerNum == 0) {
            completedRounds++;
            isFirstTurn = true;
            newRound();
        } else if (currentPlayerNum == players.size() - 1 && isFirstTurn) {
            isFirstTurn = false;
            return;
        }

        if (isFirstTurn) {
            currentPlayerNum++;
        } else {
            currentPlayerNum--;
        }
        if (skipTurnPlayerList.contains(getCurrentPlayer())) {
            skipTurnPlayerList.remove(getCurrentPlayer());
            nextTurn();
            return;
        }

        if (suspendedPlayers.contains(getCurrentPlayer())) {
            nextTurn();
            return;
        }

        startTurnAndNotify();
    }

    /**
     * return the list of players
     *
     * @return list of players
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * return the current player
     *
     * @return current player
     */
    public Player getCurrentPlayer() {
        return players.get(currentPlayerNum);
    }

    /**
     * get the draft pool
     *
     * @return draft pool
     */
    public List<Dice> getDraftPool() {
        return draftPool;
    }

    public List<ToolCard> getToolCards() {
        return new ArrayList<>(toolCards.keySet());
    }

    /**
     * Suspend the given player for either not ending its turn within
     * the given amount of time or for losing the connection with the
     * server.
     *
     * @param player the player to suspend
     */
    public void suspendPlayer(Player player) {
        suspendedPlayers.add(player);
        Controller.getInstance().send(new PlayerSuspendedEvent(player.getPlayerId()));
        if (players.size() - suspendedPlayers.size() == 1) {
            endGame();
            return;
        }

        // If we are suspending the current player, move to the next turn
        if (getCurrentPlayer().equals(player)) {
            nextTurn();
        }
    }

    /**
     * @param player current player
     */
    private void enforceCurrentPlayer(Player player) {
        if (!getCurrentPlayer().equals(player)) {
            throw new IllegalStateException("Not the current player");
        }
    }

    /**
     * Place a new dice on the window frame
     *
     * @param player   the player performing this move
     * @param position the target position
     * @param dice     the dice to place
     */
    public void placeDice(Player player, Position position, Dice dice) {
        enforceCurrentPlayer(player);
        if (moveDone) {
            throw new IllegalStateException("Move already done");
        }
        Dice draftDice = draftPool.remove(draftPool.indexOf(dice));
        getCurrentPlayer().getWindowFrame().placeDice(draftDice, position);
        moveDone = true;
    }


    /**
     * return the number of favor token you have to spend
     *
     * @param toolCard given tool card
     * @return number of token it cost
     */
    private int requiredTokens(ToolCard toolCard) {
        return toolCards.get(toolCard) == 0 ? TOKENS_PER_FIRST_CARD_USE : TOKENS_PER_CARD_USE;
    }

    /**
     * return true or false according to you can or not use a toolcard
     *
     * @param toolCard tool card you want to use
     * @return true - able to use tool card, false - not able to use tool card
     */
    public boolean canUseToolCard(Player player, ToolCard toolCard) {
        enforceCurrentPlayer(player);
        if (toolCardInUse != null || toolCardUsed) {
            return false;
        }

        if (toolCard == null || !toolCards.containsKey(toolCard)) {
            return false;
        }

        if (!toolCard.canUseCard(isFirstTurn, moveDone)) {
            return false;
        }

        if (getCurrentPlayer().getFavorTokensCount() < requiredTokens(toolCard)) {
            return false;
        }

        return true;
    }

    /**
     * Use a ToolCard. The ToolCard is only "activated" and will enable
     * the use of different methods and change the rules for the positioning
     * of the dices: {@link WindowFrame#isPositionValid(Dice, Position, ToolCard)}.
     *
     * @param toolCard given tool card
     */
    public void useToolCard(Player player, ToolCard toolCard) {
        enforceCurrentPlayer(player);
        if (!canUseToolCard(player, toolCard)) {
            throw new IllegalArgumentException("Can't use this ToolCard");
        }
        getCurrentPlayer().useFavorTokens(requiredTokens(toolCard));
        toolCards.put(toolCard, toolCards.get(toolCard) + 1);
        toolCardInUse = toolCard;
    }

    public boolean isToolCardUsed(Player player) {
        enforceCurrentPlayer(player);

        return toolCardUsed;
    }

    public boolean isToolCardInUse(Player player) {
        enforceCurrentPlayer(player);
        return toolCardInUse != null;
    }

    /**
     * according to a tool card effect change the value of a dice
     *
     * @param position where put the dice
     * @param increase the value
     */
    public void changeDiceValue(Player player, Position position, boolean increase) {
        enforceCurrentPlayer(player);
        if (toolCardUsed) {
            throw new IllegalStateException("The ToolCard has been used already");
        }
        Dice dice = getCurrentPlayer().getWindowFrame().getPlacedDices().get(position);
        if (dice == null) {
            throw new IllegalStateException("Invalid dice");
        }
        if (toolCardInUse != null && toolCardInUse.canChangeDiceValue(dice.getNumber(), increase)) {
            dice.setNumber(increase ? dice.getNumber() + 1 : dice.getNumber() - 1);
            toolCardUsed = true;
        } else {
            throw new IllegalStateException("Invalid ToolCard use");
        }
    }

    /**
     * Move an already placed dice. The positioning rules depend on the ToolCard
     * currently in use. Move enabled by {@link it.polimi.se2018.model.toolcards.ToolCard2}
     * and {@link it.polimi.se2018.model.toolcards.ToolCard3}
     *
     * @param player      the player performing this move
     * @param curPosition the position of the dice to move
     * @param newPosition the target position of the dice
     */
    public void movePlacedDice(Player player, Position curPosition, Position newPosition) {
        enforceCurrentPlayer(player);
        if (toolCardUsed) {
            throw new IllegalStateException("The ToolCard has been used already");
        }
        Dice dice = getCurrentPlayer().getWindowFrame().getPlacedDices().get(curPosition);
        if (dice == null) {
            throw new IllegalStateException("Invalid dice");
        }
        if (toolCardInUse != null && toolCardInUse.canMovePlacedDice() &&
                getCurrentPlayer().getWindowFrame().isPositionValid(dice, newPosition, toolCardInUse)) {
            getCurrentPlayer().getWindowFrame().placeDice(dice, newPosition);
            toolCardUsed = true;
        } else {
            throw new IllegalStateException("Invalid ToolCard use");
        }
    }

    /**
     * Move two placed dices respecting all the positioning rules. Move enabled by
     * {@link it.polimi.se2018.model.toolcards.ToolCard4}.
     *
     * @param player       the player performing this move
     * @param curPosition1 the position of the first dice
     * @param newPosition1 the target position of the first dice
     * @param curPosition2 the position of the second dice
     * @param newPosition2 the target position of the second dice
     */
    public void movePlacedDices(Player player, Position curPosition1, Position newPosition1, Position curPosition2, Position newPosition2) {
        enforceCurrentPlayer(player);
        if (toolCardUsed) {
            throw new IllegalStateException("The ToolCard has been used already");
        }
        Dice dice1 = getCurrentPlayer().getWindowFrame().getPlacedDices().get(curPosition1);
        Dice dice2 = getCurrentPlayer().getWindowFrame().getPlacedDices().get(curPosition2);
        if (dice1 == null || dice2 == null || dice1.equals(dice2) || newPosition1.equals(newPosition2)) {
            throw new IllegalStateException("Invalid dice");
        }
        if (toolCardInUse != null && toolCardInUse.canMovePlacedDice() &&
                // Make sure both the positions are valid before we move any of the dices,
                // we don't want to throw an exception after we moved the first dice
                getCurrentPlayer().getWindowFrame().isPositionValid(dice1, newPosition1, toolCardInUse) &&
                getCurrentPlayer().getWindowFrame().isPositionValid(dice2, newPosition2, toolCardInUse)) {
            getCurrentPlayer().getWindowFrame().placeDice(dice1, newPosition1);
            getCurrentPlayer().getWindowFrame().placeDice(dice2, newPosition2);
            toolCardUsed = true;
        } else {
            throw new IllegalStateException("Invalid ToolCard use");
        }
    }

    /**
     * Swap a dice of the track with one of the draft pool. Move enabled by
     * {@link it.polimi.se2018.model.toolcards.ToolCard5}.
     *
     * @param player    the player performing this move
     * @param draftDice the dice of the draft pool
     * @param trackDice the dice of the track
     */
    public void swapTrackDice(Player player, Dice draftDice, Dice trackDice) {
        enforceCurrentPlayer(player);
        if (toolCardUsed) {
            throw new IllegalStateException("The ToolCard has been used already");
        }
        if (toolCardInUse == null || !toolCardInUse.canSwapDices()) {
            throw new IllegalStateException("Invalid ToolCard");
        }
        if (roundTrackDices.size() == 0) {
            throw new IllegalStateException("The round track is empty");
        }
        if (!draftPool.contains(draftDice)) {
            throw new IllegalArgumentException("The given dice is not from the pool");
        }
        if (!roundTrackDices.contains(trackDice)) {
            throw new IllegalArgumentException("The given dice is not in the round track");
        }
        roundTrackDices.add(draftPool.remove(draftPool.indexOf(draftDice)));
        draftPool.add(roundTrackDices.remove(roundTrackDices.indexOf(trackDice)));
    }

    /**
     * Get the draft pool dice that has been rethrown with
     * {@link #rethrowDice(Player, Dice)}.
     *
     * @param player the player performing this move
     * @return the rethrown dice
     * @
     */
    public Dice getRethrownDice(Player player) {
        enforceCurrentPlayer(player);
        return new Dice(rethrownDice.getColor(), rethrownDice.getNumber());
    }

    /**
     * Rethrow one dice of the draft pool to be later repositioned using
     * {@link #repositionRethrownDice(Player, Position)}. Move enabled by
     * {@link it.polimi.se2018.model.toolcards.ToolCard6}.
     *
     * @param player the player performing this move
     * @param dice   the dice of the draft pool to rethrow
     */
    public void rethrowDice(Player player, Dice dice) {
        enforceCurrentPlayer(player);
        if (toolCardUsed) {
            throw new IllegalStateException("The ToolCard has been used already");
        }
        if (toolCardInUse == null || !toolCardInUse.canRethrowDice()) {
            throw new IllegalStateException("Invalid ToolCard");
        }
        if (!draftPool.contains(dice)) {
            throw new IllegalArgumentException("The given dice is not from the pool");
        }
        draftPool.get(draftPool.indexOf(dice)).setRandomNumber();
        toolCardUsed = true;
        rethrownDice = dice;
    }

    /**
     * Reposition the draft pool dice that has been rethrown using
     * {@link #rethrowDice(Player, Dice)}. Move enabled by
     * {@link it.polimi.se2018.model.toolcards.ToolCard6}.
     *
     * @param player   the player performing this move
     * @param position the target position of the rethrown dice
     */
    public void repositionRethrownDice(Player player, Position position) {
        enforceCurrentPlayer(player);
        if (rethrownDice == null) {
            throw new IllegalStateException("No dice to reposition");
        }
        getCurrentPlayer().getWindowFrame().placeDice(rethrownDice, position);
        placeDice(player, position, rethrownDice);
        rethrownDice = null;
    }

    /**
     * Shake all the dices of the draft pool. Move enabled by
     * {@link it.polimi.se2018.model.toolcards.ToolCard7}.
     *
     * @param player the player performing this move
     */
    public void shakeDices(Player player) {
        enforceCurrentPlayer(player);
        if (toolCardInUse == null || !toolCardInUse.canShakeDices()) {
            throw new IllegalStateException("Invalid ToolCard");
        }
        for (Dice dice : draftPool) {
            dice.setRandomNumber();
        }
        toolCardUsed = true;
    }

    /**
     * Place the second dice in the same turn. This player will skip the next
     * turn. Move enabled by {@link it.polimi.se2018.model.toolcards.ToolCard8}.
     * FIXME: This move can be done before placing the first dice.
     *
     * @param player   the player performing this move
     * @param position the target position of the second dice
     * @param dice     the dice to place
     */
    public void placeSecondDice(Player player, Position position, Dice dice) {
        enforceCurrentPlayer(player);
        if (toolCardInUse == null || !toolCardInUse.canPlaceTwoDices()) {
            throw new IllegalStateException("Invalid ToolCard");
        }
        Dice draftDice = draftPool.remove(draftPool.indexOf(dice));
        getCurrentPlayer().getWindowFrame().placeDice(draftDice, position);
        skipTurnPlayerList.add(getCurrentPlayer());
        toolCardUsed = true;
    }

    /**
     * Place a dice into a position that is not adjacent to that of any other dice.
     * Move enabled by {@link it.polimi.se2018.model.toolcards.ToolCard9}.
     *
     * @param player   the player performing this move
     * @param position the target position of the dice
     * @param dice     the dice to place
     */
    public void placeNotAdjacentDice(Player player, Position position, Dice dice) {
        enforceCurrentPlayer(player);
        if (toolCardInUse == null || !toolCardInUse.notAdjacent()) {
            throw new IllegalStateException("Invalid ToolCard");
        }
        Dice draftDice = draftPool.remove(draftPool.indexOf(dice));
        getCurrentPlayer().getWindowFrame().placeDice(draftDice, position, toolCardInUse);
        toolCardUsed = true;
    }

    /**
     * Flip the value of a dice of the draft pool. Move enabled by
     * {@link it.polimi.se2018.model.toolcards.ToolCard10}.
     *
     * @param player the player performing this move
     * @param dice   the dice of the draf pool to flip
     */
    public void flipDice(Player player, Dice dice) {
        enforceCurrentPlayer(player);
        if (toolCardInUse == null || !toolCardInUse.canFlipDice()) {
            throw new IllegalStateException("Invalid ToolCard");
        }
        Dice foundDraftDice = null;
        for (Dice draftDice : draftPool) {
            if (draftDice.equals(dice)) {
                foundDraftDice = draftDice;
                break;
            }
        }
        if (foundDraftDice == null) {
            throw new IllegalArgumentException("Dice not found");
        }
        foundDraftDice.setNumber(7 - dice.getNumber());
        toolCardUsed = true;
    }

    /**
     * Replace a dice of the draft pool with a dice in the dice bag.
     * Move enabled by {@link it.polimi.se2018.model.toolcards.ToolCard11}.
     *
     * @param player        the player performing this move
     * @param draftPoolDice the dice of the draft pool to replace
     */
    public void replaceDice(Player player, Dice draftPoolDice) {
        enforceCurrentPlayer(player);
        if (toolCardInUse == null || !toolCardInUse.canReplaceDice()) {
            throw new IllegalStateException("Invalid ToolCard");
        }
        if (!draftPool.contains(draftPoolDice)) {
            throw new IllegalArgumentException("Invalid dice");
        }
        diceBag.addDice(draftPool.remove(draftPool.indexOf(draftPoolDice)));
        newDice = diceBag.drawDice();
        draftPool.add(newDice);
        toolCardUsed = true;
    }

    public Dice getNewDice(Player player) {
        enforceCurrentPlayer(player);
        return new Dice(newDice.getColor(), newDice.getNumber());
    }

    /**
     * Place the dice that has been drawn after using
     * {@link it.polimi.se2018.model.toolcards.ToolCard11}
     * choosing its value.
     *
     * @param player   the player performing this move
     * @param number   the value of the dice
     * @param position the target position
     */
    public void placeNewDice(Player player, int number, Position position) {
        enforceCurrentPlayer(player);
        Dice diceCopy = new Dice(newDice.getColor(), newDice.getNumber());
        diceCopy.setNumber(number);
        if (getCurrentPlayer().getWindowFrame().isPositionValid(diceCopy, position, toolCardInUse)) {
            newDice.setNumber(number);
            getCurrentPlayer().getWindowFrame().placeDice(newDice, position);
            newDice = null;
        } else {
            throw new IllegalArgumentException("Given position is not valid");
        }
    }


    /**
     * Move two dices of the same color of a dice of the track.
     * Move associated to {@link it.polimi.se2018.model.toolcards.ToolCard12}.
     *
     * @param player the player performing this move
     * @param trackDice the dice of the track
     * @param oldPosition1 the position of the first dice to move
     * @param newPosition1 the target position of the first dice
     * @param oldPosition2 the position of the second dice to move
     * @param newPosition2 the target position of the second dice
     */

    public void moveDices(Player player, Dice trackDice, Position oldPosition1, Position newPosition1, Position oldPosition2, Position newPosition2) {
        enforceCurrentPlayer(player);
        if (!roundTrackDices.contains(trackDice)) {
            throw new IllegalArgumentException("Invalid track dice");
        }
        if (toolCardInUse == null || !toolCardInUse.canMovePlacedDice()) {
            throw new IllegalStateException("Invalid ToolCard");
        }

        Dice dice1 = getCurrentPlayer().getWindowFrame().getPlacedDices().get(oldPosition1);
        if (dice1.getColor() != trackDice.getColor()) {
            throw new IllegalArgumentException("Invalid dice");
        }

        // placeDice() does this already, but we want to move both the dices or none of them
        if (getCurrentPlayer().getWindowFrame().isPositionValid(dice1, newPosition1, null)) {
            throw new IllegalArgumentException("Invalid position");
        }


        if (oldPosition2 != null) {
            Dice dice2 = getCurrentPlayer().getWindowFrame().getPlacedDices().get(oldPosition2);
            if (dice1.getColor() != trackDice.getColor()) {
                throw new IllegalArgumentException("Invalid dice");
            }
            getCurrentPlayer().getWindowFrame().placeDice(dice2, newPosition2);
        }

        // Move dice1 now so that we don't move it and then throw an exception because of dice2
        getCurrentPlayer().getWindowFrame().placeDice(dice1, newPosition1);

        toolCardUsed = true;
    }
}
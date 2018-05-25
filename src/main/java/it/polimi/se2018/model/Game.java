package it.polimi.se2018.model;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Game {

    private static final int TOTAL_ROUND_NUM = 10;
    private static final int PUBLIC_OBJECTIVE_CARDS_NUM = 3;
    private static final int TOOL_CARDS_NUM = 3;
    private static final int TOKENS_PER_FIRST_CARD_USE = 1;
    private static final int TOKENS_PER_CARD_USE = 2;
    private static int TURN_TIMEOUT_SECONDS = 30; // TODO: don't use a constant

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

    /**
     * create all the classes for the game
     * @param players list of players
     */
    public Game(List<Player> players) {
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
            privateObjectiveCards.add(privateObjectiveCardsDeck.remove(0));
            privateObjectiveCards.add(privateObjectiveCardsDeck.remove(0));
            player.setObjectiveCards(privateObjectiveCards);
        }

        toolCards = new HashMap<>();
        List<ToolCard> toolCardsDeck = new ToolCardsDeck().getCards();
        Collections.shuffle(toolCardsDeck);
        for (int i = 0; i < TOOL_CARDS_NUM; i++) {
            toolCards.put(toolCardsDeck.remove(0), 0);
        }

        roundTrackDices = new ArrayList<>();
        skipTurnPlayerList = new ArrayList<>();

        turnTimeOutRunnable = new Runnable() {
            @Override
            public void run() {
            synchronized (Game.this) {
                if (!turnCompleted) {
                    suspendedPlayers.add(getCurrentPlayer());
                    if (players.size() - suspendedPlayers.size() == 1) {
                        endGame();
                        return;
                    }
                }
                nextTurn();
                }
            }
        };
        suspendedPlayers = new HashSet<>();
        newRound();
    }

    /**
     * return the total round counter
     * @return total rounds completed
     */
    public boolean isGameOver() {
        return completedRounds == TOTAL_ROUND_NUM || gameOver;
    }

    public void endGame() {
        gameOver = true;
    }

    /**
     * switch the game to a new round and call the methods to draw a dice
     */
    private void newRound() {
        if (isGameOver()) {
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
    }

    public synchronized void completeTurn() {
        // FIXME: Should this be possible if toolCardInUse is set, but toolCardUsed isn't?
        turnCompleted = true;
        nextTurn();
    }

    /**
     * reset card for the new player, and switch the player to the next
     */
    public synchronized void startTurnTimer() { if (scheduledTurnTimer != null && !scheduledTurnTimer.isDone()) {
            scheduledTurnTimer.cancel(true);
        }
        scheduledTurnTimer = scheduledExecutor.schedule(turnTimeOutRunnable, TURN_TIMEOUT_SECONDS, TimeUnit.SECONDS);
    }

        public synchronized void nextTurn() {
        if (isGameOver()) {
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
        startTurnTimer();

    }

    /**
     * return the list of players
     * @return list of players
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * return the current player
     * @return current player
     */
    public Player getCurrentPlayer() {
        return players.get(currentPlayerNum);
    }

    /**
     * get the draft pool
     * @return draft pool
     */
    public List<Dice> getDraftPool() {
        return draftPool;
    }

    /**
     * place the given dice in a given position
     * @param position where place
     * @param dice to place
     */
    public void placeDice(Position position, Dice dice) {
        if (moveDone) {
            throw new IllegalStateException("Move already done");
        }
        Dice draftDice = draftPool.remove(draftPool.indexOf(dice));
        getCurrentPlayer().getWindowFrame().placeDice(draftDice, position);
        moveDone = true;
    }

    /**
     * return the number of favor token you have to spend
     * @param toolCard given tool card
     * @return number of token it cost
     */
    private int requiredTokens(ToolCard toolCard) {
        return toolCards.get(toolCard) == 0 ? TOKENS_PER_FIRST_CARD_USE : TOKENS_PER_CARD_USE;
    }

    /**
     * return true or false according to you can or not use a toolcard
     * @param toolCard tool card you want to use
     * @return true - able to use tool card, false - not able to use tool card
     */
    public boolean canUseToolCard(ToolCard toolCard) {
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
     *
     * @param toolCard given tool card
     */
    public void useToolCard(ToolCard toolCard) {
        if (!canUseToolCard(toolCard)) {
            throw new IllegalArgumentException("Can't use this ToolCard");
        }
        getCurrentPlayer().useFavorTokens(requiredTokens(toolCard));
        toolCards.put(toolCard, toolCards.get(toolCard) + 1);
        toolCardInUse = toolCard;
    }

    public boolean isToolCardUsed() {
        return toolCardUsed;
    }

    public boolean isToolCardInUse() {
        return toolCardInUse != null;
    }

    /**
     * according to a tool card effect change the value of a dice
     * @param position where put the dice
     * @param increase the value
     */
    public void changeDiceValue(Position position, boolean increase) {
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

    public void movePlacedDice(Position curPosition, Position newPosition) {
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

    public void movePlacedDices(Position curPosition1, Position newPosition1,
                                Position curPosition2, Position newPosition2) {
        if (toolCardUsed) {
            throw new IllegalStateException("The ToolCard has been used already");
        }
        Dice dice1 = getCurrentPlayer().getWindowFrame().getPlacedDices().get(curPosition1);
        Dice dice2 = getCurrentPlayer().getWindowFrame().getPlacedDices().get(curPosition2);
        if (dice1 == null || dice2 == null) {
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

    public void swapTrackDice(Dice draftDice, Dice trackDice) {
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

    public Dice getRethrownDice() {
        return new Dice(rethrownDice.getColor(), rethrownDice.getNumber());
    }

    public void rethrowDice(Dice dice) {
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

    public void repositionRethrownDice(Position position) {
        if (rethrownDice == null) {
            throw new IllegalStateException("No dice to reposition");
        }
        getCurrentPlayer().getWindowFrame().placeDice(rethrownDice, position);
        placeDice(position, rethrownDice);
        rethrownDice = null;
    }

    public void shakeDices() {
        if (toolCardInUse == null || !toolCardInUse.canShakeDices()) {
            throw new IllegalStateException("Invalid ToolCard");
        }
        for (Dice dice : draftPool) {
            dice.setRandomNumber();
        }
        toolCardUsed = true;
    }

    public void placeSecondDice(Position position, Dice dice) {
        if (toolCardInUse == null || !toolCardInUse.canPlaceTwoDices()) {
            throw new IllegalStateException("Invalid ToolCard");
        }
        Dice draftDice = draftPool.remove(draftPool.indexOf(dice));
        getCurrentPlayer().getWindowFrame().placeDice(draftDice, position);
        skipTurnPlayerList.add(getCurrentPlayer());
        toolCardUsed = true;
    }

    public void placeNotAdjacentDice(Position position, Dice dice) {
        if (toolCardInUse == null || !toolCardInUse.notAdjacent()) {
            throw new IllegalStateException("Invalid ToolCard");
        }
        Dice draftDice = draftPool.remove(draftPool.indexOf(dice));
        getCurrentPlayer().getWindowFrame().placeDice(draftDice, position, toolCardInUse);
        toolCardUsed = true;
    }

    // FIXME: quale dado? Uno posizionato o uno della riserva?
    public void flipDice(Position position) {
        if (toolCardInUse == null || !toolCardInUse.canFlipDice()) {
            throw new IllegalStateException("Invalid ToolCard");
        }
        Dice dice = getCurrentPlayer().getWindowFrame().getPlacedDices().get(position);
        if (dice == null) {
            throw new IllegalStateException("Invalid dice");
        }
        dice.setNumber(7 - dice.getNumber());
        toolCardUsed = true;
    }

    public void replaceDice(Dice draftPoolDice) {
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

    public Dice getNewDice() {
        return new Dice(newDice.getColor(), newDice.getNumber());
    }

    public void placeNewDice(int number, Position position) {
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

    public void moveDice(Dice trackDice, Position oldPosition1, Position newPosition1) {
        moveDices(trackDice, oldPosition1, newPosition1, null, null);
    }

    public void moveDices(Dice trackDice, Position oldPosition1, Position newPosition1,
                          Position oldPosition2, Position newPosition2) {
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

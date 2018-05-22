package it.polimi.se2018.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {

    private static final int TOTAL_ROUND_NUM = 10;
    private static final int PUBLIC_OBJECTIVE_CARDS_NUM = 3;
    private static final int TOOL_CARDS_NUM = 3;
    private static final int TOKENS_PER_FIRST_CARD_USE = 1;
    private static final int TOKENS_PER_CARD_USE = 2;

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

    /**
     * create all the classes for the game
     * @param players list of players
     */
    public Game(List<Player> players) {
        this.players = players;

        diceBag = new DiceBag();

        publicObjectiveCards = new ArrayList<>();
        List<ObjectiveCard> publicObjectiveCardsDeck = new PublicObjectiveCardsDeck().getCards();
        Collections.shuffle(publicObjectiveCardsDeck);
        for (int i = 0; i < PUBLIC_OBJECTIVE_CARDS_NUM; i++) {
            publicObjectiveCards.add(publicObjectiveCardsDeck.remove(0));
        }

        toolCards = new HashMap<>();
        List<ToolCard> toolCardsDeck = new ToolCardsDeck().getCards();
        Collections.shuffle(toolCardsDeck);
        for (int i = 0; i < TOOL_CARDS_NUM; i++) {
            toolCards.put(toolCardsDeck.remove(0), 0);
        }

        newRound();
    }

    /**
     * return the total round counter
     * @return total rounds completed
     */
    public boolean isGameOver() {
        return completedRounds == TOTAL_ROUND_NUM;
    }

    /**
     * switch the game to a new round and call the methods to draw a dice
     */
    private void newRound() {
        if (isGameOver()) {
            return;
        }

        draftPool = new ArrayList<>();
        for (int i = 0; i < players.size() + 1; i++) {
            draftPool.add(diceBag.drawDice());
        }
    }

    /**
     * reset card for the new player, and switch the player to the next
     */
    public void nextPlayer() {
        if (isGameOver()) {
            return;
        }

        // Reset card for the new player
        toolCardInUse = null;
        toolCardUsed = false;
        // and the move flag
        moveDone = false;

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

        if (!toolCard.canUseCard(isFirstTurn)) {
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
}

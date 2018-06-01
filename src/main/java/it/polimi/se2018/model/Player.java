package it.polimi.se2018.model;

import java.util.ArrayList;
import java.util.List;

public class Player {

    public enum Color {
        PURPLE,
        RED,
        BLUE,
        GREEN,
    }

    private final String playerId;
    private Game game;
    private final Color playerColor;
    private final List<WindowPatternCard> windowPatternCards;
    private WindowFrame windowFrame;
    private final List<ObjectiveCard> objectiveCards;
    private int favorTokensCount;

    private boolean isReady;

    /**
     * create an object player
     *
     * @param playerId       playerId
     * @param windowPattern1 windowPattern1
     * @param windowPattern2 windowPattern2
     * @param playerColor    playerColor
     */
    public Player(String playerId, List<WindowPatternCard> windowPatternCards, Color playerColor) {
        this.playerId = playerId;
        this.windowPatternCards = windowPatternCards;
        this.playerColor = playerColor;
        objectiveCards = new ArrayList<>();
    }

    public String getPlayerId() {
        return playerId;
    }

    protected void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    /**
     * return the player color
     *
     * @return player color
     */
    public Color getPlayerColor() {
        return playerColor;
    }

    /**
     * return if the player is ready
     *
     * @return is ready
     */
    public boolean isReady() {
        return isReady;
    }

    /**
     * set the player to ready
     *
     * @param windowNumber windowNumber
     */
    public void setReady(int windowNumber, boolean front) {
        if (isReady()) {
            throw new IllegalStateException("Pattern already chosen");
        }
        if (windowNumber < 0 || windowNumber > windowPatternCards.size()) {
            throw new IllegalArgumentException("Invalid pattern number");
        }
        isReady = true;
        favorTokensCount = getWindowFrame().getWindowPattern().getDifficulty();
        if (front) {
            windowFrame = new WindowFrame(windowPatternCards.get(windowNumber).getFront());
        } else {
            windowFrame = new WindowFrame(windowPatternCards.get(windowNumber).getBack());
        }
    }

    /**
     * return the window frame chosen
     *
     * @return window frame
     */
    public WindowFrame getWindowFrame() {
        return windowFrame;
    }

    /**
     * return the number of favor token player has
     *
     * @return number of favor token
     */
    public int getFavorTokensCount() {
        return favorTokensCount;
    }

    /**
     * subtract the number of favor token that you use
     *
     * @param num number of used favore token
     */
    public void useFavorTokens(int num) {
        if (favorTokensCount - num < 0) {
            throw new IllegalArgumentException("Not enough token");
        }
        favorTokensCount -= num;
    }

    public void setObjectiveCards(List<ObjectiveCard> cards) {
        objectiveCards.addAll(cards);
    }
}

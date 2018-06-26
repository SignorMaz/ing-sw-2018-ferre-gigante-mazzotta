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
    private final List<ObjectiveCard> privateObjectiveCards;
    private int favorTokensCount;

    private boolean isReady;

    /**
     * @param playerId           id of the player
     * @param windowPatternCards draft pattern
     * @param playerColor        color of the player
     */
    public Player(String playerId, List<WindowPatternCard> windowPatternCards, Color playerColor) {
        if (playerId == null) {
            throw new IllegalArgumentException("Invalid player ID");
        }
        this.playerId = playerId;
        this.windowPatternCards = windowPatternCards;
        this.playerColor = playerColor;
        privateObjectiveCards = new ArrayList<>();
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
     * @param windowPattern the WindowPattern to use for the game
     */
    public void setReady(WindowPattern windowPattern) {
        if (isReady()) {
            throw new IllegalStateException("Pattern already chosen");
        }
        WindowPattern foundPattern = null;
        for (WindowPatternCard patternCard : getWindowPatternCards()) {
            if (patternCard.getBack().getPattern().equals(windowPattern.getPattern())) {
                foundPattern = windowPattern;
                break;
            }
            if (patternCard.getFront().getPattern().equals(windowPattern.getPattern())) {
                foundPattern = windowPattern;
                break;
            }
        }
        if (foundPattern == null) {
            throw new IllegalArgumentException("Invalid WindowPattern");
        }
        isReady = true;
        windowFrame = new WindowFrame(foundPattern);
        favorTokensCount = windowFrame.getWindowPattern().getDifficulty();

        getGame().tryStart();
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

    public void setPrivateObjectiveCards(List<ObjectiveCard> cards) {
        privateObjectiveCards.addAll(cards);
    }

    public List<ObjectiveCard> getPrivateObjectiveCards() {
        return privateObjectiveCards;
    }

    public List<WindowPatternCard> getWindowPatternCards() {
        return windowPatternCards;
    }
}

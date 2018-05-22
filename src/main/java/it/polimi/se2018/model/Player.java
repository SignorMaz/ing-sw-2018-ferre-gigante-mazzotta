package it.polimi.se2018.model;

import it.polimi.se2018.Observer;
import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.controller.events.Event;import java.util.ArrayList;
import java.util.List;

public class Player implements Observer {

    public enum Color {
        PURPLE,
        RED,
        BLUE,
        GREEN,
    }

    private final String playerId;
    private final Color playerColor;
    private final List<WindowPattern> windowPatterns;
    private WindowFrame windowFrame;
    private final List<ObjectiveCard> objectiveCards;
    private int favorTokensCount;

    private boolean isReady;

    /**
     * create an object player
     * @param playerId playerId
     * @param windowPattern1 windowPattern1
     * @param windowPattern2 windowPattern2
     * @param playerColor playerColor
     */
    public Player(String playerId, WindowPattern windowPattern1, WindowPattern windowPattern2, Color playerColor) {
        this.playerId = playerId;        this.windowPatterns = new ArrayList<>();
        windowPatterns.add(windowPattern1);
        windowPatterns.add(windowPattern2);
        this.playerColor = playerColor;
        objectiveCards = new ArrayList<>();

    }

        @Override
    public void send(Event event) {
        Controller.getInstance().send(event);
    }

    public String getPlayerId() {
        return playerId;
    }

    /**
     * return the player color
     * @return player color
     */
    public Color getPlayerColor() {
        return playerColor;
    }

    /**
     * return if the player is ready
     * @return is ready
     */
    public boolean isReady() {
        return isReady;
    }

    /**
     * set the player to ready
     * @param windowNumber windowNumber
     */
    public void setReady(int windowNumber) {
        if (isReady()) {
            throw new IllegalStateException("Pattern already chosen");
        }
        if (windowNumber < 0 || windowNumber > 1) {
            throw new IllegalArgumentException("Invalid pattern number");
        }
        isReady = true;
        windowFrame = new WindowFrame(windowPatterns.get(windowNumber));
        favorTokensCount = getWindowFrame().getWindowPattern().getDifficulty();
    }

    public List<WindowPattern> getWindowPatterns() {
        return windowPatterns;
    }

    /**
     * return the window frame chosen
     * @return window frame
     */
    public WindowFrame getWindowFrame() {
        return windowFrame;
    }

    /**
     * return the number of favor token player has
     * @return number of favor token
     */
    public int getFavorTokensCount() {
        return favorTokensCount;
    }

    /**
     * subtract the number of favor token that you use
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

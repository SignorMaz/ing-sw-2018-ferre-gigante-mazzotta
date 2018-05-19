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

    private final Color playerColor;
    private final List<WindowPattern> windowPatterns;
    private WindowFrame windowFrame;
    private int favorTokensCount;

    private boolean isReady;

    public Player(WindowPattern windowPattern1, WindowPattern windowPattern2, Color playerColor) {
        this.windowPatterns = new ArrayList<>();
        windowPatterns.add(windowPattern1);
        windowPatterns.add(windowPattern2);
        this.playerColor = playerColor;
    }

    public Color getPlayerColor() {
        return playerColor;
    }

    public boolean isReady() {
        return isReady;
    }

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

    public WindowFrame getWindowFrame() {
        return windowFrame;
    }

    public int getFavorTokensCount() {
        return favorTokensCount;
    }

    public void useFavorTokens(int num) {
        if (favorTokensCount - num < 0) {
            throw new IllegalArgumentException("Not enough token");
        }
        favorTokensCount -= num;
    }
}

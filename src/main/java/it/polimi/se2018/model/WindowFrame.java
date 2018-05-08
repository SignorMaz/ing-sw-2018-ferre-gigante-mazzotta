package it.polimi.se2018.model;

import java.util.HashMap;
import java.util.Map;

public class WindowFrame {
    private final Map<Position, Dice> placedDices = new HashMap<>();
    private final WindowPattern windowPattern;

    public WindowFrame(WindowPattern windowPattern) {
        this.windowPattern = windowPattern;
    }

    public WindowPattern getWindowPattern() {
        return windowPattern;
    }

    public Map<Position, Dice> getPlacedDices() {
        return placedDices;
    }
}

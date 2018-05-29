package it.polimi.se2018.model;

import java.util.ArrayList;
import java.util.List;

public class WindowPatternsDeck {
    private final List<WindowPatternCard> windowPatternCards;

    public WindowPatternsDeck() {
        windowPatternCards = new ArrayList<>();
    }

    public List<WindowPatternCard> getWindowPatternCards() {
        return windowPatternCards;
    }
}

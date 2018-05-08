package it.polimi.se2018.model;

import java.util.ArrayList;
import java.util.List;

public class WindowPatternsDeck {
    private final List<WindowPattern> windowPatterns;

    public WindowPatternsDeck() {
        windowPatterns = new ArrayList<>();
        windowPatterns.add(new WindowPattern1());
    }

    public List<WindowPattern> getWindowPatterns() {
        return windowPatterns;
    }
}

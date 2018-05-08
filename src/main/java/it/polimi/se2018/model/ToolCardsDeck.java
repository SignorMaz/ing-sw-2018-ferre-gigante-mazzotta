package it.polimi.se2018.model;

import java.util.ArrayList;
import java.util.List;

public class ToolCardsDeck {
    private final List<ToolCard> cards;

    public ToolCardsDeck() {
        cards = new ArrayList<>();
        cards.add(new ToolCard1());
    }

    public List<ToolCard> getCards() {
        return cards;
    }
}

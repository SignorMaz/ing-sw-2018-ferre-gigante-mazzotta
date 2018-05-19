package it.polimi.se2018.model;

import it.polimi.se2018.model.toolcards.*;
import java.util.ArrayList;
import java.util.List;

public class ToolCardsDeck {
    private final List<ToolCard> cards;

    public ToolCardsDeck() {
        cards = new ArrayList<>();
        cards.add(new ToolCard1());
        cards.add(new ToolCard2());
        cards.add(new ToolCard3());
        cards.add(new ToolCard4());
        cards.add(new ToolCard5());
        cards.add(new ToolCard6());
        cards.add(new ToolCard7());
        cards.add(new ToolCard8());
        cards.add(new ToolCard9());
        cards.add(new ToolCard10());
        cards.add(new ToolCard11());
        cards.add(new ToolCard12());
    }

    public List<ToolCard> getCards() {
        return cards;
    }
}

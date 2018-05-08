package it.polimi.se2018.model;

import java.util.ArrayList;
import java.util.List;

public class PrivateObjectiveCardsDeck {
    private final List<ObjectiveCard> cards;

    public PrivateObjectiveCardsDeck() {
        cards = new ArrayList<>();
        cards.add(new PrivateObjectiveCard1());
    }
}

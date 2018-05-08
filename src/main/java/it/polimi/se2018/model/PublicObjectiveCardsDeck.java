package it.polimi.se2018.model;

import java.util.ArrayList;
import java.util.List;

public class PublicObjectiveCardsDeck {
    private final List<ObjectiveCard> cards;

    public PublicObjectiveCardsDeck() {
        cards = new ArrayList<>();
        cards.add(new PublicObjectiveCard1());
    }

    public List<ObjectiveCard> getCards() {
        return cards;
    }
}

package it.polimi.se2018.model;

import it.polimi.se2018.model.objectivecards.*;

import java.util.LinkedList;
import java.util.List;

public class PublicObjectiveCardsDeck {
    private final List<ObjectiveCard> cards;

    public PublicObjectiveCardsDeck() {
        cards = new LinkedList<>();
        cards.add(new PublicObjectiveCard1());
        cards.add(new PublicObjectiveCard2());
        cards.add(new PublicObjectiveCard3());
        cards.add(new PublicObjectiveCard4());
        cards.add(new PublicObjectiveCard5());
        cards.add(new PublicObjectiveCard6());
        cards.add(new PublicObjectiveCard7());
        cards.add(new PublicObjectiveCard8());
        cards.add(new PublicObjectiveCard10());
    }

    public List<ObjectiveCard> getCards() {
        return cards;
    }
}

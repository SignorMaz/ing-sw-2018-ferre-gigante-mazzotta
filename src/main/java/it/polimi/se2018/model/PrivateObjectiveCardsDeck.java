package it.polimi.se2018.model;

import it.polimi.se2018.model.objectivecards.*;

import java.util.LinkedList;
import java.util.List;

public class PrivateObjectiveCardsDeck {
    private final List<ObjectiveCard> cards;

    public PrivateObjectiveCardsDeck() {
        cards = new LinkedList<>();
        cards.add(new PrivateObjectiveCard1());
        cards.add(new PrivateObjectiveCard2());
        cards.add(new PrivateObjectiveCard3());
        cards.add(new PrivateObjectiveCard4());
        cards.add(new PrivateObjectiveCard5());
    }

    public List<ObjectiveCard> getCards() {
        return cards;
    }
}

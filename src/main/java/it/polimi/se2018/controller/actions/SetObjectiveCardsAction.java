package it.polimi.se2018.controller.actions;

import it.polimi.se2018.model.ObjectiveCard;
import it.polimi.se2018.model.Player;

import java.util.List;

public class SetObjectiveCardsAction extends Action {
    private final List<ObjectiveCard> objectiveCards;

    public SetObjectiveCardsAction(String playerId, List<ObjectiveCard> objectiveCards) {
        super(playerId);
        this.objectiveCards = objectiveCards;
    }

    @Override
    public void perform(Player player) {
        player.setObjectiveCards(objectiveCards);
    }
}

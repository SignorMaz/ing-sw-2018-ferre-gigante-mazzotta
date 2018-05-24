package it.polimi.se2018.controller.actions;

import it.polimi.se2018.controller.Controller;
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
    public void doAction() {
        Player player = Controller.getInstance().getPlayer(getPlayerId());
        player.setObjectiveCards(objectiveCards);
    }
}

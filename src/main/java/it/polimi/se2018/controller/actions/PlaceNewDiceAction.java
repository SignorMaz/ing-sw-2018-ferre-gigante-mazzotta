package it.polimi.se2018.controller.actions;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.model.ToolCard;

public class PlaceNewDiceAction extends Action {

    private final int number;
    private final Position position;

    public PlaceNewDiceAction(int number, Position position) {
        this.number = number;
        this.position = position;
    }

    @Override
    public void perform(Player player) {
        player.getGame().placeNewDice(player, number, position);
    }
}

package it.polimi.se2018.controller.actions;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.Position;

/**
 * Action for {@link it.polimi.se2018.model.toolcards.ToolCard11}.
 *
 * This Action must be performed after {@link ReplaceDiceAction}.
 */
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

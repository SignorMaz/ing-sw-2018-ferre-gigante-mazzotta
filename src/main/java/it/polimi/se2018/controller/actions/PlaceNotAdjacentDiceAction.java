package it.polimi.se2018.controller.actions;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.Position;

/**
 * Action for {@link it.polimi.se2018.model.toolcards.ToolCard9}.
 */
public class PlaceNotAdjacentDiceAction extends Action {

    private final Position position;
    private final Dice dice;

    public PlaceNotAdjacentDiceAction(Position position, Dice dice) {
        this.position = position;
        this.dice = dice;
    }

    @Override
    public void perform(Player player) {
        player.getGame().placeNotAdjacentDice(player, position, dice);
    }
}

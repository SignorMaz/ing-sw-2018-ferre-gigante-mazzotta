package it.polimi.se2018.controller.actions;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.Position;

/**
 * Action for {@link it.polimi.se2018.model.toolcards.ToolCard2} and
 * {@link it.polimi.se2018.model.toolcards.ToolCard3}.
 */
public class MovePlacedDiceAction extends Action {

    private final Position curPosition;
    private final Position newPosition;

    public MovePlacedDiceAction(Position curPosition, Position newPosition) {
        this.curPosition = curPosition;
        this.newPosition = newPosition;
    }

    @Override
    public void perform(Player player) {
        player.getGame().movePlacedDice(player, curPosition, newPosition);
    }
}

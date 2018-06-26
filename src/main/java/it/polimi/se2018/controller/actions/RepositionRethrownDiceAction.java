package it.polimi.se2018.controller.actions;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.Position;

/**
 * Action for {@link it.polimi.se2018.model.toolcards.ToolCard6}.
 *
 * This Action must be performed after {@link RethrowDiceAction}.
 */
public class RepositionRethrownDiceAction extends Action {

    private final Position position;
    public RepositionRethrownDiceAction(Position position) {
        this.position = position;
    }

    @Override
    public void perform(Player player) {
        player.getGame().repositionRethrownDice(player, position);
    }
}

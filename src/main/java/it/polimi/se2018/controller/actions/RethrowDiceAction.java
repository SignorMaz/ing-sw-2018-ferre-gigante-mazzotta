package it.polimi.se2018.controller.actions;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Player;

/**
 * Action for {@link it.polimi.se2018.model.toolcards.ToolCard6}.
 *
 * This Action must be performed before {@link RepositionRethrownDiceAction}.
 */
public class RethrowDiceAction extends Action {

    private final Dice dice;

    public RethrowDiceAction(Dice dice) {
        this.dice = dice;
    }

    @Override
    public void perform(Player player) {
        player.getGame().rethrowDice(player, dice);
    }
}

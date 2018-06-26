package it.polimi.se2018.controller.actions;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Player;

/**
 * Action for {@link it.polimi.se2018.model.toolcards.ToolCard10}.
 */

public class FlipDiceAction extends Action {

    private final Dice dice;

    public FlipDiceAction(Dice dice) {
        this.dice = dice;
    }

    @Override
    public void perform(Player player) {
        player.getGame().flipDice(player, dice);
    }
}

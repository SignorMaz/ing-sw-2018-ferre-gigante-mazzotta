package it.polimi.se2018.controller.actions;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.Position;

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

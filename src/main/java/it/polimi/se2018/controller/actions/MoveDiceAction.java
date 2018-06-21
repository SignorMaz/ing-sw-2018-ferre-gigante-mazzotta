package it.polimi.se2018.controller.actions;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.model.ToolCard;

public class MoveDiceAction extends Action {

    private final Dice trackDice;
    private final Position oldPosition1;
    private final Position newPosition1;

    public MoveDiceAction(Dice trackDice, Position oldPosition1, Position newPosition1) {
        this.trackDice = trackDice;
        this.oldPosition1 = oldPosition1;
        this.newPosition1 = newPosition1;
    }

    @Override
    public void perform(Player player) {
        player.getGame().moveDice(player, trackDice, oldPosition1, newPosition1);
    }
}

package it.polimi.se2018.controller.actions;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.model.ToolCard;

public class MoveDicesAction extends Action {

    private final Dice trackDice;
    private final Position oldPosition1;
    private final Position newPosition1;
    private final Position oldPosition2;
    private final Position newPosition2;

    public MoveDicesAction(Dice trackDice,
                           Position oldPosition1, Position newPosition1,
                           Position oldPosition2, Position newPosition2) {
        this.trackDice = trackDice;
        this.oldPosition1 = oldPosition1;
        this.newPosition1 = newPosition1;
        this.oldPosition2 = oldPosition2;
        this.newPosition2 = newPosition2;
    }

    @Override
    public void perform(Player player) {
        player.getGame().moveDices(player, trackDice,
                oldPosition1, newPosition1,
                oldPosition2, newPosition2);
    }
}

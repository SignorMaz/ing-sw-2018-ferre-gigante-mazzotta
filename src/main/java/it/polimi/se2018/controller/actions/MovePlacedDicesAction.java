package it.polimi.se2018.controller.actions;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.Position;

/**
 * Action for {@link it.polimi.se2018.model.toolcards.ToolCard4}.
 */
public class MovePlacedDicesAction extends Action {

    private final Position curPosition1;
    private final Position newPosition1;
    private final Position curPosition2;
    private final Position newPosition2;

    public MovePlacedDicesAction(Position curPosition1, Position newPosition1,
                                 Position curPosition2, Position newPosition2) {
        this.curPosition1 = curPosition1;
        this.newPosition1 = newPosition1;
        this.curPosition2 = curPosition2;
        this.newPosition2 = newPosition2;
    }

    @Override
    public void perform(Player player) {
        player.getGame().movePlacedDices(player, curPosition1, newPosition1, curPosition2, newPosition2);
    }
}

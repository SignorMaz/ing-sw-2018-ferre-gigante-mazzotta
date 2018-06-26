package it.polimi.se2018.controller.actions;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.Position;

/**
 * Action for {@link it.polimi.se2018.model.toolcards.ToolCard1}.
 */
public class ChangeDiceValueAction extends Action {

    private final Position position;
    private final boolean increase;

    public ChangeDiceValueAction(Position position, boolean increase) {
        this.position = position;
        this.increase = increase;
    }

    @Override
    public void perform(Player player) {
        player.getGame().changeDiceValue(player, position, increase);
    }
}

package it.polimi.se2018.controller.actions;

import it.polimi.se2018.model.Player;

/**
 * Action for {@link it.polimi.se2018.model.toolcards.ToolCard7}.
 */
public class ShakeDicesAction extends Action {

    @Override
    public void perform(Player player) {
        player.getGame().shakeDices(player);
    }
}

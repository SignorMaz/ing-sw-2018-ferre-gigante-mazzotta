package it.polimi.se2018.controller.actions;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.ToolCard;

public class UseToolCardAction extends Action {

    private final ToolCard toolCard;

    public UseToolCardAction(ToolCard toolCard) {
        this.toolCard = toolCard;
    }

    @Override
    public void perform(Player player) {
        player.getGame().useToolCard(player, toolCard);
    }
}
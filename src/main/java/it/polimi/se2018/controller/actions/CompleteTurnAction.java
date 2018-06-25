package it.polimi.se2018.controller.actions;

import it.polimi.se2018.model.Player;

public class CompleteTurnAction extends Action {

    public CompleteTurnAction() {
    }

    @Override
    public void perform(Player player) {
        player.getGame().completeTurn();
    }
}
package it.polimi.se2018.controller.actions;

import it.polimi.se2018.model.Player;

public class ShakeDicesAction extends Action {

    public ShakeDicesAction() {
    }

    @Override
    public void perform(Player player) {
        player.getGame().shakeDices(player);
    }
}

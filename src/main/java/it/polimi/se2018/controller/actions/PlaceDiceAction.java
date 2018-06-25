package it.polimi.se2018.controller.actions;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.Position;

public class PlaceDiceAction extends Action {

    private final Dice dice;
    private final Position position;

    public PlaceDiceAction(Dice dice, Position position) {
        this.dice = dice;
        this.position = position;
    }

    @Override
    public void perform(Player player) {
        player.getGame().placeDice(player, position, dice);
    }
}

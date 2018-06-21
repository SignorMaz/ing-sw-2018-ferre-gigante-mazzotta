package it.polimi.se2018.controller.actions;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.Position;

public class PlaceSecondDiceAction extends Action {

    private final Position position;
    private final Dice dice;

    public PlaceSecondDiceAction(Position position, Dice dice) {
        this.position = position;
        this.dice = dice;
    }

    @Override
    public void perform(Player player) {
        player.getGame().placeSecondDice(player, position, dice);
    }
}

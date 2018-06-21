package it.polimi.se2018.controller.actions;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Player;

public class ReplaceDiceAction extends Action {

    private final Dice draftDice;

    public ReplaceDiceAction(Dice draftDice) {
        this.draftDice = draftDice;
    }

    @Override
    public void perform(Player player) {
        player.getGame().replaceDice(player, draftDice);
    }
}

package it.polimi.se2018.controller.actions;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Player;

public class SwapTrackDiceAction extends Action {

    private final Dice draftDice;
    private final Dice trackDice;

    public SwapTrackDiceAction(Dice draftDice, Dice trackDice) {
        this.draftDice = draftDice;
        this.trackDice = trackDice;
    }

    @Override
    public void perform(Player player) {
        player.getGame().swapTrackDice(player, draftDice, trackDice);
    }
}

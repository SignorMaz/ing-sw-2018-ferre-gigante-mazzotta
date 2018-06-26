package it.polimi.se2018.controller.actions;

import it.polimi.se2018.model.Player;

import java.io.Serializable;

public abstract class Action implements Serializable {

    private String playerId;

    /**
     * Set the ID of the player associated to this Action.
     *
     * @param playerId the ID of the player
     */
    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    /**
     * Get the ID of the player associated to this Action.
     *
     * @return the ID of the player
     */
    public String getPlayerId() {
        return playerId;
    }

    /**
     *  Perform this action.
     *
     * @param player The player performing this Action.
     */
    public abstract void perform(Player player);
}
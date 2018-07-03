package it.polimi.se2018.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Abstract class representing a generic ToolCard. All the methods defined
 * in this class can alter the standard rules of the game. The definition
 * provided by default have no actual effect on the rules, each ToolCard
 * extending this class needs to override specific methods. This allows to
 * treat all the cards in the same way and it allows to add and remove new
 * cards without the need of updating all the subclasses.
 */
public abstract class ToolCard implements Serializable {

    // Needed to allow the auto-generation of equals() and hashcode()
    private final int number = getNumber();

    /**
     * @return the name of this ToolCard
     */
    public abstract String getName();

    /**
     * @return the description of this ToolCard
     */
    public abstract String getDescription();

    /**
     * @return the number of this ToolCard
     */
    public abstract int getNumber();

    /**
     * {@link it.polimi.se2018.model.toolcards.ToolCard2}
     *
     * @return true if this card allows to ignore the color restrictions when placing the dices
     */
    public boolean ignoreColor() {
        return false;
    }

    /**
     * {@link it.polimi.se2018.model.toolcards.ToolCard3}
     *
     * @return true if this card allows to ignore the value restrictions when placing the dices
     */
    public boolean ignoreNumber() {
        return false;
    }

    /**
     * {@link it.polimi.se2018.model.toolcards.ToolCard9}
     *
     * @return true if this card allows to place dices on the board not adjacent to other dices
     */
    public boolean notAdjacent() {
        return false;
    }

    /**
     * {@link it.polimi.se2018.model.toolcards.ToolCard8}
     *
     * @param isFirstTurn true if this is the first turn
     * @param moveDone true if the player already did this turn's move
     * @return true if this card can be used
     */
    public boolean canUseCard(boolean isFirstTurn, boolean moveDone) {
        return true;
    }

    /**
     * {@link it.polimi.se2018.model.toolcards.ToolCard1}
     *
     * @param value The dice value to change
     * @param increase true if the value should be increased, false otherwise
     * @return true if this card allows to change the value of a chosen dice
     */
    public boolean canChangeDiceValue(int value, boolean increase) {
        return false;
    }

    /**
     * {@link it.polimi.se2018.model.toolcards.ToolCard2}
     * {@link it.polimi.se2018.model.toolcards.ToolCard3}
     *
     * @return true if this card allows to move an already placed dice
     */
    public boolean canMovePlacedDice() {
        return false;
    }

    /**
     * {@link it.polimi.se2018.model.toolcards.ToolCard4}
     *
     * @return true if this card allows to move an already placed dice
     */
    public boolean canMoveTwoPlacedDices() {
        return false;
    }

    /**
     * {@link it.polimi.se2018.model.toolcards.ToolCard12}
     *
     * @return true if this card allows to move an already placed dice
     */
    public boolean canMoveTwoPlacedDicesWithTrack() {
        return false;
    }

    /**
     * {@link it.polimi.se2018.model.toolcards.ToolCard5}
     *
     * @return true if this card allows to swap a dice of the draft pool with one of the track
     */
    public boolean canSwapDices() {
        return false;
    }

    /**
     * {@link it.polimi.se2018.model.toolcards.ToolCard6}
     *
     * @return true if this card allows to re-throw a dice of the draft pool
     */
    public boolean canRethrowDice() {
        return false;
    }

    /**
     * {@link it.polimi.se2018.model.toolcards.ToolCard7}
     *
     * @return true if this card allows to shake the dices of the draft pool
     */
    public boolean canShakeDices() {
        return false;
    }

    /**
     * {@link it.polimi.se2018.model.toolcards.ToolCard8}
     *
     * @return true if this card allows to place two dices in the same turn
     */
    public boolean canPlaceTwoDices() {
        return false;
    }

    /**
     * {@link it.polimi.se2018.model.toolcards.ToolCard10}
     *
     * @return true if this card allows to flip a dice
     */
    public boolean canFlipDice() {
        return false;
    }

    /**
     * {@link it.polimi.se2018.model.toolcards.ToolCard11}
     *
     * @return true if this card allows to replace a dice of the draft pool with a new one
     */
    public boolean canReplaceDice() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToolCard toolCard = (ToolCard) o;
        return number == toolCard.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

}

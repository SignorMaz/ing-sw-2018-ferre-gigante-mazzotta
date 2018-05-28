package it.polimi.se2018.model;

import java.io.Serializable;
import java.util.Objects;

public abstract class ToolCard implements Serializable {

    // Needed to allow the auto-generation of equals() and hashcode()
    private final int number = getNumber();

    public abstract String getName();

    public abstract String getDescription();

    public abstract int getNumber();

    public boolean ignoreColor() {
        return false;
    }

    public boolean ignoreNumber() {
        return false;
    }

    public boolean notAdjacent() {
        return false;
    }

    public boolean canUseCard(boolean isFirstTurn, boolean moveDone) {
        return true;
    }

    public boolean canChangeDiceValue(int value, boolean increase) {
        return false;
    }

    public boolean canSwapDices() {
        return false;
    }

    public boolean canRethrowDice() {
        return false;
    }

    public boolean canShakeDices() {
        return false;
    }

    public boolean canPlaceTwoDices() {
        return true;
    }

    public boolean canFlipDice() {
        return false;
    }

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

    public boolean canMovePlacedDice() {
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

}

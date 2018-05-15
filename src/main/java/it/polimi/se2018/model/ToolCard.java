package it.polimi.se2018.model;

public abstract class ToolCard {

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
}

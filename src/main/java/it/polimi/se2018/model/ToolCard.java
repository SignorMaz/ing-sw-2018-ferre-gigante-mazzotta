package it.polimi.se2018.model;

public interface ToolCard {

    String getName();

    String getDescription();

    int getNumber();

    boolean ignoreColor();

    boolean ignoreNumber();

    boolean notAdjacent();
}

package it.polimi.se2018.model;

import java.util.Map;

public interface WindowPattern {
    int ROWS = 5;
    int COLUMNS = 4;

    String getName();
    int getDifficulty();
    Map<Position, WindowCell> getPattern();
}

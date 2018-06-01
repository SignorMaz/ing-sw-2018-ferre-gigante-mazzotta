package it.polimi.se2018.model;

import java.io.Serializable;
import java.util.Map;

public interface WindowPattern extends Serializable {
    int ROWS = 4;
    int COLUMNS = 5;

    String getName();

    int getDifficulty();

    Map<Position, WindowCell> getPattern();
}

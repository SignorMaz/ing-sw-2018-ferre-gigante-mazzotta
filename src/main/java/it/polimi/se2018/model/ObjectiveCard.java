package it.polimi.se2018.model;

import java.io.Serializable;

public interface ObjectiveCard extends Serializable {
    String getName();

    String getDescription();

    int getPoints(WindowFrame windowFrame);
}

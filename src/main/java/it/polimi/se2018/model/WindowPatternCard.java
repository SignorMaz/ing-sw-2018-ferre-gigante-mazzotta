package it.polimi.se2018.model;

import java.io.Serializable;

public interface WindowPatternCard extends Serializable {
    WindowPattern getFront();

    WindowPattern getBack();
}
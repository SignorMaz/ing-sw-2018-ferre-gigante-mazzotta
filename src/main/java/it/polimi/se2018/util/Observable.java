package it.polimi.se2018.util;

import it.polimi.se2018.controller.actions.Action;
import it.polimi.se2018.controller.events.Event;

public interface Observable {
    void send(Event event);

    void handle(Action action);
}

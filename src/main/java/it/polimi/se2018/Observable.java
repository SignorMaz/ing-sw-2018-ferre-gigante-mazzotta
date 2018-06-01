package it.polimi.se2018;

import it.polimi.se2018.controller.actions.Action;
import it.polimi.se2018.controller.events.Event;

import java.io.IOException;

public interface Observable {
    void send(Event event) throws IOException;

    void handle(Action action);
}

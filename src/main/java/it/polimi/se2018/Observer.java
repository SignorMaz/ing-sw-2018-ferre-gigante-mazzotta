package it.polimi.se2018;

import it.polimi.se2018.controller.actions.Action;
import it.polimi.se2018.controller.events.Event;

import java.io.IOException;

public interface Observer {
    void send(Action action) throws IOException;

    void handle(Event event);
}
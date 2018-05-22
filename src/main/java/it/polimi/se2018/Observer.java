package it.polimi.se2018;

        import it.polimi.se2018.controller.events.Event;

        public interface Observer {
    void send(Event event);
}
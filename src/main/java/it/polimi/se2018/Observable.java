package it.polimi.se2018;

        import it.polimi.se2018.controller.actions.Action;

        public interface Observable {
    void send(Action action);
}

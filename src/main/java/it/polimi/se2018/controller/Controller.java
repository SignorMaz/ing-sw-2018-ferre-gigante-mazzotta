package it.polimi.se2018.controller;

import it.polimi.se2018.Observable;
import it.polimi.se2018.Observer;
import it.polimi.se2018.controller.actions.Action;
import it.polimi.se2018.controller.events.Event;

        public class Controller implements Observer, Observable {

        private static final Controller INSTANCE = new Controller();

        public static Controller getInstance() {
            return INSTANCE;
        }

            @Override
    public void send(Event event) {
                    // TODO
                        }

            @Override
    public void send(Action action) {
                    action.doAction();
                }
    }
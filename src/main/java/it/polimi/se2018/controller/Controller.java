package it.polimi.se2018.controller;

        public class Controller {

            private static final Controller INSTANCE = new Controller();

            public static Controller getInstance() {
                return INSTANCE;
            }
}
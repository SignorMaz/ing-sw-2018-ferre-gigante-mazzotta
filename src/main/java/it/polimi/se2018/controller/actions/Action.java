package it.polimi.se2018.controller.actions;

        import java.io.Serializable;

        public abstract class Action implements Serializable {

            private final String playerId;

            Action(String playerId) {
                this.playerId = playerId;
            }

            public String getPlayerId() {
                return playerId;
            }

            public abstract void doAction();
}
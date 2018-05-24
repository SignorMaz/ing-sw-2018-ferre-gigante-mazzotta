package it.polimi.se2018.controller.events;

        import it.polimi.se2018.view.PlayerView;

        import java.io.Serializable;

        public abstract class Event implements Serializable {

            private final String playerId;

            public Event(String playerId) {
                this.playerId = playerId;
            }

            public String getPlayerId() {
                return playerId;
            }

            public abstract void update(PlayerView playerView);
}
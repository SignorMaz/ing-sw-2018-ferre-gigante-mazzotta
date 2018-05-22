package it.polimi.se2018.controller;

        import java.util.Objects;

        class Client {

            private final String playerId;

            public Client(String playerId) {
                this.playerId = playerId;
            }

            public String getPlayerId() {
                return playerId;
            }

            @Override
    public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Client that = (Client) o;
                return Objects.equals(playerId, that.playerId);
            }

            @Override
    public int hashCode() {
                return Objects.hash(playerId);
            }
}
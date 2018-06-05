package it.polimi.se2018.controller.events;

import it.polimi.se2018.view.PlayerView;

public class DraftPoolChangedEvent extends Event {
    public DraftPoolChangedEvent(String playerId) {
        super(playerId);
    }

    @Override
    public void update(PlayerView playerView) {
        playerView.onDraftPoolChanged();
    }
}

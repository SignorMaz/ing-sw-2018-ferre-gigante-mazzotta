package it.polimi.se2018.controller.events;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.view.PlayerView;

import java.util.ArrayList;
import java.util.List;

public class DraftPoolChangedEvent extends Event {

    private final ArrayList<Dice> draftPool;

    public DraftPoolChangedEvent(String playerId, List<Dice> draftPool) {
        super(playerId);
        this.draftPool = new ArrayList<>(draftPool);
    }

    @Override
    public void update(PlayerView playerView) {
        playerView.onDraftPoolChanged(draftPool);
    }
}

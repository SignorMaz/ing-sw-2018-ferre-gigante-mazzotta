package it.polimi.se2018.view;

import it.polimi.se2018.controller.actions.Action;
import it.polimi.se2018.controller.events.InitialSetupEvent;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.model.WindowPattern;

import java.util.Map;

public interface PlayerView {
    PlayerViewBase getPlayerViewBase();

    void onLogin(boolean result);
    void onInitialSetup(InitialSetupEvent.Data data);
    void onDicePlaced(String playerPlacingDice, Position position, Dice dice);
    void onDraftPoolChanged();
    void onGameOver();
    void onInvalidAction(Action action);
    void onNewTurn();
    void onPointsChanged(int points);
    void onTokensChanged(int tokens);
    void onGameStarted(Map<String, WindowPattern> windowPatternMap);
}

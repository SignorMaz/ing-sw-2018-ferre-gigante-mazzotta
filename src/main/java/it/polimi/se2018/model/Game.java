package it.polimi.se2018.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {

    private static final int TOTAL_ROUND_NUM = 10;
    private static final int PUBLIC_OBJECTIVE_CARDS_NUM = 3;
    private static final int TOOL_CARDS_NUM = 3;

    private final List<Player> players;
    private int currentPlayerNum = 0;
    private boolean isFirstTurn = false;
    private int completedRounds = 0;
    private List<Dice> draftPool;
    private final DiceBag diceBag;
    private final List<ObjectiveCard> publicObjectiveCards;
    private final List<ToolCard> toolCards;

    public Game(List<Player> players) {
        this.players = players;

        diceBag = new DiceBag();

        publicObjectiveCards = new ArrayList<>();
        List<ObjectiveCard> publicObjectiveCardsDeck = new PublicObjectiveCardsDeck().getCards();
        Collections.shuffle(publicObjectiveCardsDeck);
        for (int i = 0; i < PUBLIC_OBJECTIVE_CARDS_NUM; i++) {
            publicObjectiveCards.add(publicObjectiveCardsDeck.remove(0));
        }

        toolCards = new ArrayList<>();
        List<ToolCard> toolCardsDeck = new ToolCardsDeck().getCards();
        Collections.shuffle(toolCardsDeck);
        for (int i = 0; i < TOOL_CARDS_NUM; i++) {
            toolCards.add(toolCardsDeck.remove(0));
        }

        newRound();
    }

    public boolean isGameOver() {
        return completedRounds == TOTAL_ROUND_NUM;
    }

    private void newRound() {
        if (isGameOver()) {
            return;
        }

        draftPool = new ArrayList<>();
        for (int i = 0; i < players.size() + 1; i++) {
            draftPool.add(diceBag.drawDice());
        }
    }

    public void nextPlayer() {
        if (isGameOver()) {
            return;
        }

        if (!isFirstTurn && currentPlayerNum == 0) {
            completedRounds++;
            isFirstTurn = true;
            newRound();
        } else if (currentPlayerNum == players.size() - 1 && isFirstTurn) {
            isFirstTurn = false;
            return;
        }

        if (isFirstTurn) {
            currentPlayerNum++;
        } else {
            currentPlayerNum--;
        }
    }

    public List<Player> getPlayers() {
        return players;
    }
}

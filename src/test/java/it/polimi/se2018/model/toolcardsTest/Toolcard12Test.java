package it.polimi.se2018.model.toolcardsTest;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.toolcards.ToolCard12;
import it.polimi.se2018.util.WindowPatternLoader;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Toolcard12Test {
    @Test
    public void retuturnsTest() {
        ToolCard tooltest = new ToolCard12();

        assertEquals("Taglierina Manuale", tooltest.getName());
        assertEquals("Muovi fino a due dadi dello stesso colore di un solo dado sul Tracciato dei Round. Devi rispettare tutte le restrizioni di piazzamento.", tooltest.getDescription());
        assertEquals(12, tooltest.getNumber());
        assertTrue(tooltest.canMoveTwoPlacedDicesWithTrack());
    }

    private static void resetDraftPool(List<Dice> draftPool, Dice dice) {
        int size = draftPool.size();
        draftPool.clear();
        for (int i = 0; i < size; i++) {
            draftPool.add(dice);
        }
    }

    @Test
    public void toolCard12MoveEffectValid() {
        WindowPatternCard windowPattern = WindowPatternLoader.loadFromResource("blank-pattern.json");
        List<WindowPatternCard> windowListTest = new ArrayList<>();
        windowListTest.add(windowPattern);
        windowListTest.add(windowPattern);

        List<Player> playerListTest = new ArrayList<>();
        Player player1 = new Player("0001", windowListTest, Player.Color.BLUE);
        Player player2 = new Player("0002", windowListTest, Player.Color.RED);
        playerListTest.add(player1);
        playerListTest.add(player2);

        List<ToolCard> toolCardsDeck = new ArrayList<>();
        toolCardsDeck.add(new ToolCard12());
        toolCardsDeck.add(new ToolCard12());
        toolCardsDeck.add(new ToolCard12());
        Game game = new Game(playerListTest, toolCardsDeck);
        player1.setReady(windowPattern.getFront());
        player2.setReady(windowPattern.getFront());

        Dice redDice = new Dice(Color.RED, 1);
        Dice blueDice = new Dice(Color.BLUE, 2);

        Position firstPosition = new Position(0, 0);
        Position secondPosition = new Position(0, 1);
        Position thirdPosition = new Position(0, 2);

        Player currentPlayer;

        currentPlayer = game.getCurrentPlayer();
        resetDraftPool(game.getDraftPool(), redDice);
        game.placeDice(currentPlayer, firstPosition, redDice);
        game.completeTurn();

        currentPlayer = game.getCurrentPlayer();
        resetDraftPool(game.getDraftPool(), redDice);
        game.placeDice(currentPlayer, firstPosition, redDice);
        game.completeTurn();

        currentPlayer = game.getCurrentPlayer();
        resetDraftPool(game.getDraftPool(), blueDice);
        game.getDraftPool().add(blueDice);
        game.placeDice(currentPlayer, secondPosition, blueDice);
        game.completeTurn();

        currentPlayer = game.getCurrentPlayer();
        resetDraftPool(game.getDraftPool(), redDice);
        game.getDraftPool().add(blueDice);
        game.placeDice(currentPlayer, secondPosition, blueDice);
        game.completeTurn();

        currentPlayer = game.getCurrentPlayer();
        resetDraftPool(game.getDraftPool(), redDice);
        game.placeDice(currentPlayer, thirdPosition, redDice);
        game.completeTurn();

        // Going back with the turns -> same player -> don't do anything
        resetDraftPool(game.getDraftPool(), redDice);
        game.completeTurn();

        currentPlayer = game.getCurrentPlayer();
        resetDraftPool(game.getDraftPool(), redDice);
        game.placeDice(currentPlayer, thirdPosition, redDice);
        game.completeTurn();

        // The track is no longer be empty and has dices equal to redDice

        Position firstPositionNew = new Position(2, 0);
        Position thirdPositionNew = new Position(2, 2);

        game.getCurrentPlayer().getWindowFrame().placeDiceUnrestricted(redDice, new Position(1, 1));
        currentPlayer = game.getCurrentPlayer();
        resetDraftPool(game.getDraftPool(), redDice);
        game.useToolCard(currentPlayer, new ToolCard12());
        game.moveDicesWithTrack(currentPlayer, redDice, firstPosition, firstPositionNew, thirdPosition, thirdPositionNew);
    }
}
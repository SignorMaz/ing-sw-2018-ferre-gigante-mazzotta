package it.polimi.se2018.model.toolcardsTest;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.toolcards.ToolCard3;
import it.polimi.se2018.util.WindowPatternLoader;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Toolcard3Test {
    @Test
    public void retuturnsTest() {
        ToolCard tooltest = new ToolCard3();

        assertEquals("Alesatore per lamina di rame", tooltest.getName());
        assertEquals("Muovi un qualsiasi dado nella tua vetrata ignorando le restrizioni di valore. Devi rispettare tutte le altre restrizioni di piazzamento.", tooltest.getDescription());
        assertEquals(3, tooltest.getNumber());
        assertTrue(tooltest.ignoreNumber());
    }

    private static void resetDraftPool(List<Dice> draftPool, Dice dice, int size) {
        draftPool.clear();
        for (int i = 0; i < size; i++) {
            draftPool.add(dice);
        }
    }

    @Test
    public void toolCard3MoveEffectValid() {
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
        toolCardsDeck.add(new ToolCard3());
        toolCardsDeck.add(new ToolCard3());
        toolCardsDeck.add(new ToolCard3());
        Game game = new Game(playerListTest, toolCardsDeck);
        player1.setReady(windowPattern.getFront());
        player2.setReady(windowPattern.getFront());

        Dice redDice = new Dice(Color.RED, 1);
        int originalDiceNumber = redDice.getNumber();

        Position dicePosition = new Position(0, 0);
        Player currentPlayer = game.getCurrentPlayer();
        resetDraftPool(game.getDraftPool(), redDice, playerListTest.size());
        game.placeDice(currentPlayer, dicePosition, redDice);
        game.completeTurn();
        currentPlayer = game.getCurrentPlayer();
        resetDraftPool(game.getDraftPool(), redDice, playerListTest.size());
        game.placeDice(currentPlayer, dicePosition, redDice);
        game.completeTurn();

        // We now have a dice on the frame, let's move it using the ToolCard

        Position newPosition = new Position(dicePosition.row + 2, dicePosition.column + 2);
        game.useToolCard(currentPlayer, new ToolCard3());
//        game.movePlacedDice(currentPlayer, dicePosition, newPosition);
    }
}
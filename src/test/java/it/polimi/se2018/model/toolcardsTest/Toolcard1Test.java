package it.polimi.se2018.model.toolcardsTest;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.toolcards.ToolCard1;
import it.polimi.se2018.util.WindowPatternLoader;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class Toolcard1Test {
    @Test
    public void retuturnsTest() {
        ToolCard tooltest = new ToolCard1();

        assertEquals("Pinza Sgrossatrice", tooltest.getName());
        assertEquals("Dopo aver scelto un dado, aumenta o diminuisci il valore del dado scelto di 1. Non puoi cambiare un 6 in 1 o un 1 in 6", tooltest.getDescription());
        assertEquals(1, tooltest.getNumber());
        assertEquals(true, tooltest.canChangeDiceValue(2, true));
        assertEquals(false, tooltest.canChangeDiceValue(1, false));
        assertEquals(true, tooltest.canChangeDiceValue(1, true));
        assertEquals(false, tooltest.canChangeDiceValue(6, true));
        assertEquals(true, tooltest.canChangeDiceValue(6, false));

    }

    private static void resetDraftPool(List<Dice> draftPool, Dice dice, int size) {
        draftPool.clear();
        for (int i = 0; i < size; i++) {
            draftPool.add(dice);
        }
    }

    @Test
    public void toolCard1MoveEffectValid() {
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
        toolCardsDeck.add(new ToolCard1());
        toolCardsDeck.add(new ToolCard1());
        toolCardsDeck.add(new ToolCard1());
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

        // We now have a dice on the frame, let's change its value with the ToolCard

        game.useToolCard(currentPlayer, new ToolCard1());
        game.changeDiceValue(currentPlayer, dicePosition, true);
        Dice placedDice = currentPlayer.getWindowFrame().getPlacedDices().get(dicePosition);
        assertEquals(originalDiceNumber + 1, placedDice.getNumber());
    }
}
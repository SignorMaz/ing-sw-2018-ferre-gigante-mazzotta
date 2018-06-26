package it.polimi.se2018.model;

import it.polimi.se2018.util.WindowPatternLoader;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class GameTest {


    @Test
    public void canUseToolcards() {
        WindowPatternCard windowPattern = WindowPatternLoader.loadFromResource("loader-pattern-test.json");
        List<WindowPatternCard> windowListTest = new ArrayList<>();
        List<Player> playerListTest = new ArrayList<>();
        windowListTest.add(windowPattern);
        windowListTest.add(windowPattern);
        ToolCard tooltest = null;
        Player player1 = new Player("0001", windowListTest, Player.Color.BLUE);
        Player player2 = new Player("0002", windowListTest, Player.Color.RED);
        playerListTest.add(player1);
        playerListTest.add(player2);
        Game game = new Game(playerListTest);
        player1.setReady(windowPattern.getFront());
        player2.setReady(windowPattern.getBack());
        Assert.assertEquals(player1, game.getCurrentPlayer());
        game.nextTurn();
        Assert.assertEquals(player2, game.getCurrentPlayer());
        game.nextTurn();
        Assert.assertEquals(player2, game.getCurrentPlayer());
        game.nextTurn();
        assertEquals(1, game.getCurrentPlayer().getFavorTokensCount());
        game.placeDice(player1, new Position(3, 4), game.getDraftPool().get(0));
        assertFalse(game.canUseToolCard(player1, tooltest)); //can't use null card
        assertTrue(game.canUseToolCard(player1, game.getToolCards().get(0)));//second turn, move done, 1 token, no toolcard used. Player1 can use all toolcards
        game.useToolCard(player1, game.getToolCards().get(0)); //use toolcard and 1 token
        assertTrue(game.isToolCardInUse(player1));
        assertFalse(game.canUseToolCard(player1, game.getToolCards().get(0))); //toolCardInUse
        assertEquals(0, game.getCurrentPlayer().getFavorTokensCount());
        game.nextTurn();
        Assert.assertEquals(player2, game.getCurrentPlayer());
        game.placeDice(player2, new Position(3, 3), game.getDraftPool().get(1));
        assertTrue(game.canUseToolCard(player2, game.getToolCards().get(1))); //second turn, move done, 4 tokens. Player2 can use all toolcards
        game.nextTurn();
        Assert.assertEquals(player2, game.getCurrentPlayer());
        game.nextTurn();
        Assert.assertEquals(player1, game.getCurrentPlayer());
        assertFalse(game.canUseToolCard(player1, game.getToolCards().get(0))); //0 token, player1 cant use toolcard
    }

    @Test
    public void suspendPlayers() {
        WindowPatternCard windowPattern = WindowPatternLoader.loadFromResource("loader-pattern-test.json");
        List<WindowPatternCard> windowListTest = new ArrayList<>();
        List<Player> playerListTest = new ArrayList<>();
        windowListTest.add(windowPattern);
        windowListTest.add(windowPattern);
        Player player1 = new Player("0001", windowListTest, Player.Color.BLUE);
        Player player2 = new Player("0002", windowListTest, Player.Color.RED);
        Player player3 = new Player("0003", windowListTest, Player.Color.PURPLE);
        playerListTest.add(player1);
        playerListTest.add(player2);
        playerListTest.add(player3);
        Game game = new Game(playerListTest);
        player1.setReady(windowPattern.getFront());
        player2.setReady(windowPattern.getBack());
        player3.setReady(windowPattern.getBack());
        game.suspendPlayer(player1);
        game.suspendPlayer(player2);
    }
}


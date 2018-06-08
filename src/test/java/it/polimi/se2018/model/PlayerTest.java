
package it.polimi.se2018.model;

import it.polimi.se2018.util.WindowPatternLoader;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class PlayerTest {

    @Test
    public void startGame() {
        WindowPatternCard windowPattern = WindowPatternLoader.loadFromResource("loader-pattern-test.json");
        List<WindowPatternCard> windowListTest = new ArrayList<>();
        windowListTest.add(windowPattern);
        windowListTest.add(windowPattern);
        Player player1 = new Player("0001", windowListTest, Player.Color.BLUE);
        assertEquals(Player.Color.BLUE, player1.getPlayerColor());
        assertFalse(player1.isReady());
        player1.setReady(1, true);
        Assert.assertEquals(1, player1.getFavorTokensCount());
        player1.useFavorTokens(1);
        Assert.assertEquals(0, player1.getFavorTokensCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTooMuchToken() {
        WindowPatternCard windowPattern = WindowPatternLoader.loadFromResource("loader-pattern-test.json");
        List<WindowPatternCard> windowListTest = new ArrayList<>();
        windowListTest.add(windowPattern);
        windowListTest.add(windowPattern);
        Player player1 = new Player("0001", windowListTest, Player.Color.BLUE);
        assertEquals(Player.Color.BLUE, player1.getPlayerColor());
        assertFalse(player1.isReady());
        player1.setReady(1, true);
        player1.useFavorTokens(2);

    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidPatternNumber() {
        WindowPatternCard windowPattern = WindowPatternLoader.loadFromResource("loader-pattern-test.json");
        List<WindowPatternCard> windowListTest = new ArrayList<>();
        windowListTest.add(windowPattern);
        windowListTest.add(windowPattern);
        Player player1 = new Player("0001", windowListTest, Player.Color.BLUE);
        assertEquals(Player.Color.BLUE, player1.getPlayerColor());
        assertFalse(player1.isReady());
        player1.setReady(4, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativePatternNumber() {
        WindowPatternCard windowPattern = WindowPatternLoader.loadFromResource("loader-pattern-test.json");
        List<WindowPatternCard> windowListTest = new ArrayList<>();
        windowListTest.add(windowPattern);
        windowListTest.add(windowPattern);
        Player player1 = new Player("0001", windowListTest, Player.Color.BLUE);
        assertEquals(Player.Color.BLUE, player1.getPlayerColor());
        assertFalse(player1.isReady());
        player1.setReady(-4, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullPlayer() {
        WindowPatternCard windowPattern = WindowPatternLoader.loadFromResource("loader-pattern-test.json");
        List<WindowPatternCard> windowListTest = new ArrayList<>();
        windowListTest.add(windowPattern);
        windowListTest.add(windowPattern);
        Player player1 = new Player(null, windowListTest, Player.Color.BLUE);
        assertEquals(Player.Color.BLUE, player1.getPlayerColor());
        assertFalse(player1.isReady());
        player1.setReady(4, true);
    }
}


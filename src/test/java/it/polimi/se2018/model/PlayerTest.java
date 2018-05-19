
package it.polimi.se2018.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;

public class PlayerTest {

    private class WindowPatternTest1 implements WindowPattern {

        private final Map<Position, WindowCell> map = new HashMap<>();

        private WindowPatternTest1() {
            map.put(new Position(0, 0), new WindowCell(Color.RED, 0));
            map.put(new Position(1, 0), new WindowCell(Color.BLANK, 0));
            map.put(new Position(2, 0), new WindowCell(Color.BLUE, 0));
            map.put(new Position(3, 0), new WindowCell(Color.GREEN, 0));

            map.put(new Position(0, 1), new WindowCell(Color.PURPLE, 0));
            map.put(new Position(1, 1), new WindowCell(Color.BLUE, 0));
            map.put(new Position(2, 1), new WindowCell(Color.BLANK, 0));
            map.put(new Position(3, 1), new WindowCell(Color.BLANK, 0));

            map.put(new Position(0, 2), new WindowCell(Color.BLANK, 6));
            map.put(new Position(1, 2), new WindowCell(Color.BLANK, 0));
            map.put(new Position(2, 2), new WindowCell(Color.BLUE, 0));
            map.put(new Position(3, 2), new WindowCell(Color.YELLOW, 0));

            map.put(new Position(0, 3), new WindowCell(Color.BLANK, 0));
            map.put(new Position(1, 3), new WindowCell(Color.BLANK, 0));
            map.put(new Position(2, 3), new WindowCell(Color.YELLOW, 0));
            map.put(new Position(3, 3), new WindowCell(Color.BLUE, 0));

            map.put(new Position(0, 4), new WindowCell(Color.BLANK, 0));
            map.put(new Position(1, 4), new WindowCell(Color.YELLOW, 0));
            map.put(new Position(2, 4), new WindowCell(Color.BLANK, 0));
            map.put(new Position(3, 4), new WindowCell(Color.BLANK, 4));
        }

        @Override
        public String getName() {
            return "Test Map1";
        }

        @Override
        public int getDifficulty() {
            return 5;
        }

        @Override
        public Map<Position, WindowCell> getPattern() {
            return map;
        }
    }

    private class WindowPatternTest2 implements WindowPattern {

        private final Map<Position, WindowCell> map = new HashMap<>();

        private WindowPatternTest2() {
            map.put(new Position(0, 0), new WindowCell(Color.BLUE, 0));
            map.put(new Position(1, 0), new WindowCell(Color.RED, 1));
            map.put(new Position(2, 0), new WindowCell(Color.BLANK, 0));
            map.put(new Position(3, 0), new WindowCell(Color.YELLOW, 3));

            map.put(new Position(0, 1), new WindowCell(Color.BLUE, 0));
            map.put(new Position(1, 1), new WindowCell(Color.PURPLE, 0));
            map.put(new Position(2, 1), new WindowCell(Color.BLANK, 3));
            map.put(new Position(3, 1), new WindowCell(Color.BLANK, 0));

            map.put(new Position(0, 2), new WindowCell(Color.BLANK, 6));
            map.put(new Position(1, 2), new WindowCell(Color.BLANK, 0));
            map.put(new Position(2, 2), new WindowCell(Color.PURPLE, 0));
            map.put(new Position(3, 2), new WindowCell(Color.YELLOW, 0));

            map.put(new Position(0, 3), new WindowCell(Color.BLANK, 0));
            map.put(new Position(1, 3), new WindowCell(Color.BLANK, 0));
            map.put(new Position(2, 3), new WindowCell(Color.YELLOW, 0));
            map.put(new Position(3, 3), new WindowCell(Color.BLUE, 0));

            map.put(new Position(0, 4), new WindowCell(Color.BLANK, 0));
            map.put(new Position(1, 4), new WindowCell(Color.BLANK, 0));
            map.put(new Position(2, 4), new WindowCell(Color.BLANK, 0));
            map.put(new Position(3, 4), new WindowCell(Color.BLANK, 4));
        }

        @Override
        public String getName() {
            return "Test Map2";
        }

        @Override
        public int getDifficulty() {
            return 5;
        }

        @Override
        public Map<Position, WindowCell> getPattern() {
            return map;
        }
    }

    @Test
    public void testReady() {
         WindowPattern windowPattern1 = new WindowPatternTest1();
        WindowPattern windowPattern2 = new WindowPatternTest2();
        Player player = new Player(windowPattern1, windowPattern2, Player.Color.BLUE);
        assertEquals(Player.Color.BLUE, player.getPlayerColor());
        assertFalse(player.isReady());
        player.setReady(1);
        Assert.assertEquals(5, player.getFavorTokensCount());
        player.useFavorTokens(3);
        Assert.assertEquals(2, player.getFavorTokensCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTooMuchToken() {
        WindowPattern windowPattern1 = new WindowPatternTest1();
        WindowPattern windowPattern2 = new WindowPatternTest2();
        Player player = new Player(windowPattern1, windowPattern2, Player.Color.BLUE);
        assertEquals(Player.Color.BLUE, player.getPlayerColor());
        assertFalse(player.isReady());
        player.setReady(1);
        player.useFavorTokens(6);
    }
}


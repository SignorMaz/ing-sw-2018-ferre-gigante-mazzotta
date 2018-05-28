package it.polimi.se2018.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GameTest {

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

            map.put(new Position(0, 2), new WindowCell(Color.BLANK, 0));
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
        Player player1 = new Player("0001", windowPattern1, windowPattern2, Player.Color.BLUE);
        player1.setReady(1);
        Player player2 = new Player("0002", windowPattern1, windowPattern2, Player.Color.RED);
        player2.setReady(0);
        List<Player> testPlayer = new ArrayList<>();
        testPlayer.add(player1);
        testPlayer.add(player2);
        Game game = new Game(testPlayer);
        Assert.assertEquals(player1,game.getCurrentPlayer());
        Assert.assertEquals(testPlayer,game.getPlayers());
        game.placeDice(new Position(2,0), game.getDraftPool().get(0));

        }
}

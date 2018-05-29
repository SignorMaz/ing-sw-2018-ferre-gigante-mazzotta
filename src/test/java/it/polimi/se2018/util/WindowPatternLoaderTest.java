package it.polimi.se2018.util;

import it.polimi.se2018.model.*;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class WindowPatternLoaderTest {

    private class WindowPatternFrontTest implements WindowPattern {

        private final Map<Position, WindowCell> map = new HashMap<>();

        private WindowPatternFrontTest() {
            map.put(new Position(0, 0), new WindowCell(Color.BLANK, 4));
            map.put(new Position(0, 1), new WindowCell(Color.BLANK, 0));
            map.put(new Position(0, 2), new WindowCell(Color.BLANK, 2));
            map.put(new Position(0, 3), new WindowCell(Color.BLANK, 5));
            map.put(new Position(0, 4), new WindowCell(Color.GREEN, 0));

            map.put(new Position(1, 0), new WindowCell(Color.BLANK, 0));
            map.put(new Position(1, 1), new WindowCell(Color.BLANK, 0));
            map.put(new Position(1, 2), new WindowCell(Color.BLANK, 6));
            map.put(new Position(1, 3), new WindowCell(Color.GREEN, 0));
            map.put(new Position(1, 4), new WindowCell(Color.BLANK, 2));

            map.put(new Position(2, 0), new WindowCell(Color.BLANK, 0));
            map.put(new Position(2, 1), new WindowCell(Color.BLANK, 3));
            map.put(new Position(2, 2), new WindowCell(Color.GREEN, 0));
            map.put(new Position(2, 3), new WindowCell(Color.BLANK, 4));
            map.put(new Position(2, 4), new WindowCell(Color.BLANK, 0));

            map.put(new Position(3, 0), new WindowCell(Color.BLANK, 5));
            map.put(new Position(3, 1), new WindowCell(Color.GREEN, 0));
            map.put(new Position(3, 2), new WindowCell(Color.BLANK, 1));
            map.put(new Position(3, 3), new WindowCell(Color.BLANK, 0));
            map.put(new Position(3, 4), new WindowCell(Color.BLANK, 0));
        }

        @Override
        public String getName() {
            return "Test pattern front";
        }

        @Override
        public int getDifficulty() {
            return 1;
        }

        @Override
        public Map<Position, WindowCell> getPattern() {
            return map;
        }
    }

    private class WindowPatternBackTest implements WindowPattern {

        private final Map<Position, WindowCell> map = new HashMap<>();

        private WindowPatternBackTest() {
            map.put(new Position(0, 0), new WindowCell(Color.BLANK, 1));
            map.put(new Position(0, 1), new WindowCell(Color.BLANK, 2));
            map.put(new Position(0, 2), new WindowCell(Color.BLANK, 3));
            map.put(new Position(0, 3), new WindowCell(Color.BLANK, 4));
            map.put(new Position(0, 4), new WindowCell(Color.GREEN, 0));

            map.put(new Position(1, 0), new WindowCell(Color.RED, 0));
            map.put(new Position(1, 1), new WindowCell(Color.BLANK, 1));
            map.put(new Position(1, 2), new WindowCell(Color.YELLOW, 6));
            map.put(new Position(1, 3), new WindowCell(Color.GREEN, 0));
            map.put(new Position(1, 4), new WindowCell(Color.BLANK, 2));

            map.put(new Position(2, 0), new WindowCell(Color.BLUE, 6));
            map.put(new Position(2, 1), new WindowCell(Color.BLANK, 3));
            map.put(new Position(2, 2), new WindowCell(Color.GREEN, 0));
            map.put(new Position(2, 3), new WindowCell(Color.BLANK, 4));
            map.put(new Position(2, 4), new WindowCell(Color.BLANK, 2));

            map.put(new Position(3, 0), new WindowCell(Color.BLANK, 5));
            map.put(new Position(3, 1), new WindowCell(Color.GREEN, 0));
            map.put(new Position(3, 2), new WindowCell(Color.BLANK, 5));
            map.put(new Position(3, 3), new WindowCell(Color.BLANK, 0));
            map.put(new Position(3, 4), new WindowCell(Color.PURPLE, 0));
        }

        @Override
        public String getName() {
            return "Test pattern back";
        }

        @Override
        public int getDifficulty() {
            return 4;
        }

        @Override
        public Map<Position, WindowCell> getPattern() {
            return map;
        }
    }

    @Test
    public void windowPatternLoaderTest() {
        WindowPatternFrontTest windowPatternFrontTest = new WindowPatternFrontTest();
        WindowPatternBackTest windowPatternBackTest = new WindowPatternBackTest();

        WindowPatternCard windowPatternCardResource = WindowPatternLoader.loadFromResource("loader-pattern-test.json");
        assertNotNull(windowPatternCardResource);

        assertEquals(windowPatternFrontTest.getName(), windowPatternCardResource.getFront().getName());
        assertEquals(windowPatternFrontTest.getDifficulty(), windowPatternCardResource.getFront().getDifficulty());
        assertEquals(windowPatternFrontTest.getPattern(), windowPatternCardResource.getFront().getPattern());

        assertEquals(windowPatternBackTest.getName(), windowPatternCardResource.getBack().getName());
        assertEquals(windowPatternBackTest.getDifficulty(), windowPatternCardResource.getBack().getDifficulty());
        assertEquals(windowPatternBackTest.getPattern(), windowPatternCardResource.getBack().getPattern());
    }
}


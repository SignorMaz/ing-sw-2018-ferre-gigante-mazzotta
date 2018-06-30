package it.polimi.se2018.model;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class WindowFrameTest {

    private class WindowPatternTest implements WindowPattern {

        private final Map<Position, WindowCell> map = new HashMap<>();

        private WindowPatternTest() {
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
            return "Test Map";
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
    public void testisPositionValid() {
        ToolCard toolCard = null;
        WindowPattern windowPattern = new WindowPatternTest();
        WindowFrame windowFrame = new WindowFrame(windowPattern);
        assertTrue(windowFrame.isPositionValid(new Dice(Color.BLUE), new Position(2, 0), toolCard));
        assertTrue(windowFrame.isPositionValid(new Dice(Color.RED), new Position(0, 0), toolCard));
        assertTrue(windowFrame.isPositionValid(new Dice(Color.RED, 6), new Position(0, 2), toolCard));
        assertTrue(windowFrame.isPositionValid(new Dice(Color.RED, 4), new Position(3, 4), toolCard));
        assertTrue(windowFrame.isPositionValid(new Dice(Color.GREEN), new Position(3, 0), toolCard));
        assertTrue(windowFrame.isPositionValid(new Dice(Color.PURPLE), new Position(0, 1), toolCard));
        assertTrue(windowFrame.isPositionValid(new Dice(Color.BLUE), new Position(3, 1), toolCard));
        assertFalse(windowFrame.isPositionValid(new Dice(Color.BLUE), new Position(2, 3), toolCard));
        assertFalse(windowFrame.isPositionValid(new Dice(Color.RED, 2), new Position(0, 2), toolCard));     //Wrong number
        assertFalse(windowFrame.isPositionValid(new Dice(Color.RED, 3), new Position(3, 4), toolCard));
        assertFalse(windowFrame.isPositionValid(new Dice(Color.YELLOW), new Position(2, 3), toolCard));         //Corner
        assertFalse(windowFrame.isPositionValid(new Dice(Color.BLUE), new Position(3, 0), toolCard));          //Color not valid
        assertFalse(windowFrame.isPositionValid(new Dice(Color.RED), new Position(2, 0), toolCard));
        assertFalse(windowFrame.isPositionValid(new Dice(Color.YELLOW), new Position(2, 0), toolCard));
        assertFalse(windowFrame.isPositionValid(new Dice(Color.GREEN), new Position(1, 4), toolCard));
        assertFalse(windowFrame.isPositionValid(new Dice(Color.GREEN), new Position(-1, 4), toolCard));        // The position doesn't exist
        assertFalse(windowFrame.isPositionValid(new Dice(Color.GREEN), new Position(1, 5), toolCard));
        assertFalse(windowFrame.isPositionValid(new Dice(Color.GREEN), new Position(4, -4), toolCard));
        windowFrame.placeDice(new Dice(Color.RED, 6), new Position(0, 0), toolCard);
        assertFalse(windowFrame.isPositionValid(new Dice(Color.BLUE, 5), new Position(1, 0), toolCard));
        assertFalse(windowFrame.isPositionValid(new Dice(Color.BLUE, 5), new Position(1, 1), toolCard));
        assertFalse(windowFrame.isPositionValid(new Dice(Color.RED, 6), new Position(0, 0), toolCard));        // The position is already occupied
        assertTrue(windowFrame.isPositionValid(new Dice(Color.BLUE), new Position(2, 0), toolCard));                // Every dice must be adjacent to an already placed dice
        assertFalse(windowFrame.isPositionValid(new Dice(Color.RED), new Position(1, 0), toolCard));       // Orthogonally adjacent dices must have different color
        assertFalse(windowFrame.isPositionValid(new Dice(Color.BLUE, 6), new Position(1, 0), toolCard));       // Orthogonally adjacent dices must have different number
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidValue() {
        ToolCard toolCard = null;
        WindowPattern windowPattern = new WindowPatternTest();
        WindowFrame windowFrame = new WindowFrame(windowPattern);
        windowFrame.placeDice(new Dice(Color.BLUE, 6), new Position(0, 0), toolCard);
    }

}


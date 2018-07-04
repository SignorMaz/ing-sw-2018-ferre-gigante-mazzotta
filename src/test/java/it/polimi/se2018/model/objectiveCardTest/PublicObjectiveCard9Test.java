package it.polimi.se2018.model.objectiveCardTest;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.objectivecards.PublicObjectiveCard9;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PublicObjectiveCard9Test {

    private class WindowPatternTest implements WindowPattern {

        private final Map<Position, WindowCell> map = new HashMap<>();

        private WindowPatternTest() {
            map.put(new Position(0, 0), new WindowCell(Color.BLANK, 0));
            map.put(new Position(1, 0), new WindowCell(Color.BLANK, 0));
            map.put(new Position(2, 0), new WindowCell(Color.BLUE, 0));
            map.put(new Position(3, 0), new WindowCell(Color.BLANK, 0));

            map.put(new Position(0, 1), new WindowCell(Color.BLANK, 0));
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
            map.put(new Position(3, 4), new WindowCell(Color.BLANK, 0));
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
    public void testGetPoints() {

        WindowPattern windowPattern = new WindowPatternTest();
        WindowFrame windowFrame = new WindowFrame(windowPattern);
        windowFrame.placeDiceUnrestricted(new Dice(Color.BLUE), new Position(2, 0));
        windowFrame.placeDiceUnrestricted(new Dice(Color.BLUE), new Position(1, 1));
        windowFrame.placeDiceUnrestricted(new Dice(Color.BLUE), new Position(2, 2));
        windowFrame.placeDiceUnrestricted(new Dice(Color.BLUE), new Position(3, 3));
        assertEquals(4, new PublicObjectiveCard9().getPoints(windowFrame));

        windowFrame.placeDiceUnrestricted(new Dice(Color.BLUE), new Position(0, 4));
        assertEquals(4, new PublicObjectiveCard9().getPoints(windowFrame));

        windowFrame.placeDiceUnrestricted(new Dice(Color.RED), new Position(0, 3));
        assertEquals(4, new PublicObjectiveCard9().getPoints(windowFrame));

        windowFrame.placeDiceUnrestricted(new Dice(Color.YELLOW), new Position(3, 2));
        windowFrame.placeDiceUnrestricted(new Dice(Color.YELLOW), new Position(2, 3));
        windowFrame.placeDiceUnrestricted(new Dice(Color.YELLOW), new Position(1, 4));
        assertEquals(7, new PublicObjectiveCard9().getPoints(windowFrame));
    }
}
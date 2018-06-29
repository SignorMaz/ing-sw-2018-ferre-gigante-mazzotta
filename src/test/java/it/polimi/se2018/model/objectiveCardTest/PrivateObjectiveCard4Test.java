package it.polimi.se2018.model.objectiveCardTest;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.objectivecards.PrivateObjectiveCard4;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PrivateObjectiveCard4Test {
    private class WindowPatternTest implements WindowPattern {

        private final Map<Position, WindowCell> map = new HashMap<>();

        private WindowPatternTest() {
            map.put(new Position(0, 0), new WindowCell(Color.BLANK, 4));
            map.put(new Position(1, 0), new WindowCell(Color.BLANK, 0));
            map.put(new Position(2, 0), new WindowCell(Color.BLANK, 0));
            map.put(new Position(3, 0), new WindowCell(Color.BLANK, 5));

            map.put(new Position(0, 1), new WindowCell(Color.BLANK, 0));
            map.put(new Position(1, 1), new WindowCell(Color.BLANK, 0));
            map.put(new Position(2, 1), new WindowCell(Color.BLANK, 3));
            map.put(new Position(3, 1), new WindowCell(Color.GREEN, 0));

            map.put(new Position(0, 2), new WindowCell(Color.BLANK, 2));
            map.put(new Position(1, 2), new WindowCell(Color.BLANK, 6));
            map.put(new Position(2, 2), new WindowCell(Color.GREEN, 0));
            map.put(new Position(3, 2), new WindowCell(Color.BLANK, 1));

            map.put(new Position(0, 3), new WindowCell(Color.BLANK, 5));
            map.put(new Position(1, 3), new WindowCell(Color.GREEN, 0));
            map.put(new Position(2, 3), new WindowCell(Color.BLANK, 5));
            map.put(new Position(3, 3), new WindowCell(Color.BLANK, 0));

            map.put(new Position(0, 4), new WindowCell(Color.GREEN, 0));
            map.put(new Position(1, 4), new WindowCell(Color.BLANK, 2));
            map.put(new Position(2, 4), new WindowCell(Color.BLANK, 0));
            map.put(new Position(3, 4), new WindowCell(Color.BLANK, 0));
        }

        @Override
        public String getName() {
            return "Test map";
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

        WindowPattern windowPattern = new PrivateObjectiveCard4Test.WindowPatternTest();
        WindowFrame windowFrame = new WindowFrame(windowPattern);

        assertEquals(0, new PrivateObjectiveCard4().getPoints(windowFrame));
        windowFrame.placeDiceUnrestricted(new Dice(Color.BLUE, 6), new Position(2, 0));
        windowFrame.placeDiceUnrestricted(new Dice(Color.RED, 5), new Position(1, 1));
        windowFrame.placeDiceUnrestricted(new Dice(Color.GREEN, 3), new Position(2, 2));
        windowFrame.placeDiceUnrestricted(new Dice(Color.GREEN, 4), new Position(3, 3));
        assertEquals(6, new PrivateObjectiveCard4().getPoints(windowFrame));

        windowFrame.placeDiceUnrestricted(new Dice(Color.GREEN, 5), new Position(0, 4));
        windowFrame.placeDiceUnrestricted(new Dice(Color.RED, 5), new Position(0, 3));
        assertEquals(6, new PrivateObjectiveCard4().getPoints(windowFrame));

        windowFrame.placeDiceUnrestricted(new Dice(Color.BLUE, 1), new Position(3, 2));
        windowFrame.placeDiceUnrestricted(new Dice(Color.YELLOW, 6), new Position(2, 4));
        windowFrame.placeDiceUnrestricted(new Dice(Color.GREEN, 6), new Position(3, 4));
        assertEquals(7, new PrivateObjectiveCard4().getPoints(windowFrame));
    }
}

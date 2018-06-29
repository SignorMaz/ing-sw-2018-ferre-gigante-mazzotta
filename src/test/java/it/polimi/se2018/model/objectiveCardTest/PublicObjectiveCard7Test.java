package it.polimi.se2018.model.objectiveCardTest;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.objectivecards.PublicObjectiveCard7;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PublicObjectiveCard7Test {

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

        assertEquals(0, new PublicObjectiveCard7().getPoints(windowFrame));
        windowFrame.placeDiceUnrestricted(new Dice(Color.BLUE, 6), new Position(2, 0));
        windowFrame.placeDiceUnrestricted(new Dice(Color.RED, 1), new Position(1, 1));
        windowFrame.placeDiceUnrestricted(new Dice(Color.YELLOW, 3), new Position(2, 2));
        windowFrame.placeDiceUnrestricted(new Dice(Color.GREEN, 4), new Position(3, 3));
        assertEquals(0, new PublicObjectiveCard7().getPoints(windowFrame));

        windowFrame.placeDiceUnrestricted(new Dice(Color.PURPLE, 5), new Position(0, 4));
        windowFrame.placeDiceUnrestricted(new Dice(Color.RED, 6), new Position(0, 3));
        assertEquals(2, new PublicObjectiveCard7().getPoints(windowFrame));

        windowFrame.placeDiceUnrestricted(new Dice(Color.BLUE, 5), new Position(3, 2));
        windowFrame.placeDiceUnrestricted(new Dice(Color.YELLOW, 6), new Position(2, 4));
        windowFrame.placeDiceUnrestricted(new Dice(Color.GREEN, 6), new Position(3, 4));
        assertEquals(4, new PublicObjectiveCard7().getPoints(windowFrame));
    }
}
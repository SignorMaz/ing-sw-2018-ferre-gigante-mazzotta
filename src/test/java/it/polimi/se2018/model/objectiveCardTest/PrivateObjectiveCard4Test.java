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

    private class PermissiveToolCard extends ToolCard {
        @Override
        public String getName() {
            return "Test ToolCard";
        }

        @Override
        public String getDescription() {
            return "Ignore color and number";
        }

        @Override
        public int getNumber() {
            return 0;
        }

        @Override
        public boolean ignoreColor() {
            return true;
        }

        @Override
        public boolean ignoreNumber() {
            return true;
        }

        @Override
        public boolean notAdjacent() {
            return true;
        }
    }

    @Test
    public void testGetPoints() {
        ToolCard toolCard = new PrivateObjectiveCard4Test.PermissiveToolCard();

        WindowPattern windowPattern = new PrivateObjectiveCard4Test.WindowPatternTest();
        WindowFrame windowFrame = new WindowFrame(windowPattern);

        assertEquals(0, new PrivateObjectiveCard4().getPoints(windowFrame));
        windowFrame.placeDice(new Dice(Color.BLUE, 6), new Position(2, 0), toolCard);
        windowFrame.placeDice(new Dice(Color.RED, 5), new Position(1, 1), toolCard);
        windowFrame.placeDice(new Dice(Color.GREEN, 3), new Position(2, 2), toolCard);
        windowFrame.placeDice(new Dice(Color.GREEN, 4), new Position(3, 3), toolCard);
        assertEquals(6, new PrivateObjectiveCard4().getPoints(windowFrame));

        windowFrame.placeDice(new Dice(Color.GREEN, 5), new Position(0, 4), toolCard);
        windowFrame.placeDice(new Dice(Color.RED, 5), new Position(0, 3), toolCard);
        assertEquals(6, new PrivateObjectiveCard4().getPoints(windowFrame));

        windowFrame.placeDice(new Dice(Color.BLUE, 1), new Position(3, 2), toolCard);
        windowFrame.placeDice(new Dice(Color.YELLOW, 6), new Position(2, 4), toolCard);
        windowFrame.placeDice(new Dice(Color.GREEN, 6), new Position(3, 4), toolCard);
        assertEquals(7, new PrivateObjectiveCard4().getPoints(windowFrame));
    }
}

package it.polimi.se2018.model.ObjectiveCardTest;

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
        ToolCard toolCard = new PermissiveToolCard();

        WindowPattern windowPattern = new WindowPatternTest();
        WindowFrame windowFrame = new WindowFrame(windowPattern);
        windowFrame.placeDice(new Dice(Color.BLUE), new Position(2, 0), toolCard);
        windowFrame.placeDice(new Dice(Color.BLUE), new Position(1, 1), toolCard);
        windowFrame.placeDice(new Dice(Color.BLUE), new Position(2, 2), toolCard);
        windowFrame.placeDice(new Dice(Color.BLUE), new Position(3, 3), toolCard);
        assertEquals(new PublicObjectiveCard9().getPoints(windowFrame), 4);

        windowFrame.placeDice(new Dice(Color.BLUE), new Position(0, 4), toolCard);
        assertEquals(new PublicObjectiveCard9().getPoints(windowFrame), 4);

        windowFrame.placeDice(new Dice(Color.RED), new Position(0, 3), toolCard);
        assertEquals(new PublicObjectiveCard9().getPoints(windowFrame), 4);

        windowFrame.placeDice(new Dice(Color.YELLOW), new Position(3, 2), toolCard);
        windowFrame.placeDice(new Dice(Color.YELLOW), new Position(2, 3), toolCard);
        windowFrame.placeDice(new Dice(Color.YELLOW), new Position(1, 4), toolCard);
        assertEquals(new PublicObjectiveCard9().getPoints(windowFrame), 7);
    }
}
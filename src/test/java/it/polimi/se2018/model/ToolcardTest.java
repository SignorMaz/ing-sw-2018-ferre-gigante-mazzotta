
package it.polimi.se2018.model;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ToolcardTest {
    @Test
    public void retuturnsTest() {
        ToolCard tooltest = new ToolCard() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public int getNumber() {
                return 0;
            }
        };

        assertFalse(tooltest.ignoreColor());
        assertFalse(tooltest.ignoreNumber());
        assertFalse(tooltest.notAdjacent());
        assertFalse(tooltest.canChangeDiceValue(1, true));
        assertFalse(tooltest.canSwapDices());
        assertFalse(tooltest.canRethrowDice());
        assertFalse(tooltest.canShakeDices());
        assertFalse(tooltest.canPlaceTwoDices());
        assertFalse(tooltest.canFlipDice());
        assertFalse(tooltest.canReplaceDice());
        assertFalse(tooltest.canMovePlacedDice());
        assertTrue(tooltest.canUseCard(true, true));

        ToolCard tooltest1 = new ToolCard() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public int getNumber() {
                return 0;
            }
        };
        assertFalse(tooltest.equals(tooltest1));
        assertEquals(31, tooltest.hashCode());
        assertTrue(tooltest.equals(tooltest));
    }
}

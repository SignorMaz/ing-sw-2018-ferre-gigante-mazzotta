package it.polimi.se2018.model.toolcardsTest;

import it.polimi.se2018.model.ToolCard;
import it.polimi.se2018.model.toolcards.ToolCard5;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Toolcard5Test {
    @Test
    public void retuturnsTest() {
        ToolCard tooltest = new ToolCard5();

        assertEquals("Taglierina circolare", tooltest.getName());
        assertEquals("Dopo aver scelto un dado, scambia quel dado con un dado sul Tracciato dei Round.", tooltest.getDescription());
        assertEquals(5, tooltest.getNumber());
        assertTrue(tooltest.canSwapDices());
    }
}
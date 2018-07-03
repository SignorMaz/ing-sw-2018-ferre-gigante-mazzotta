package it.polimi.se2018.model.toolcardsTest;

import it.polimi.se2018.model.ToolCard;
import it.polimi.se2018.model.toolcards.ToolCard4;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Toolcard4Test {
    @Test
    public void retuturnsTest() {
        ToolCard tooltest = new ToolCard4();

        assertEquals("Lathekin", tooltest.getName());
        assertEquals("Muovi esattamente due dadi, rispettando tutte le restrizioni di piazzamento.", tooltest.getDescription());
        assertEquals(4, tooltest.getNumber());
        assertTrue(tooltest.canMoveTwoPlacedDices());
    }
}
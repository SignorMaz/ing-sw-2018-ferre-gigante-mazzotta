package it.polimi.se2018.model.toolcardsTest;

import it.polimi.se2018.model.ToolCard;
import it.polimi.se2018.model.toolcards.ToolCard3;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Toolcard3Test {
    @Test
    public void retuturnsTest() {
        ToolCard tooltest = new ToolCard3();

        assertEquals("Alesatore per lamina di rame", tooltest.getName());
        assertEquals("Muovi un qualsiasi dado nella tua vetrata ignorando le restrizioni di valore. Devi rispettare tutte le altre restrizioni di piazzamento.", tooltest.getDescription());
        assertEquals(3, tooltest.getNumber());
        assertTrue(tooltest.ignoreNumber());
    }
}
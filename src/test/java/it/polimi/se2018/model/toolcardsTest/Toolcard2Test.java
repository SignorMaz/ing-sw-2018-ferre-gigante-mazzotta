package it.polimi.se2018.model.toolcardsTest;

import it.polimi.se2018.model.ToolCard;
import it.polimi.se2018.model.toolcards.ToolCard2;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Toolcard2Test {
    @Test
    public void retuturnsTest() {
        ToolCard tooltest = new ToolCard2();

        assertEquals("Pennello per Eglomise", tooltest.getName());
        assertEquals("Muovi un qualsiasi dado nella tua vetrata ignorando le restrizioni di colore. Devi rispettare tutte le altre restrizioni di piazzamento.", tooltest.getDescription());
        assertEquals(2, tooltest.getNumber());
        assertTrue(tooltest.ignoreColor());
    }
}
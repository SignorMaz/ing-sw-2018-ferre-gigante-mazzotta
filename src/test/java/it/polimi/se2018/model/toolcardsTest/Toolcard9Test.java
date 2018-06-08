package it.polimi.se2018.model.toolcardsTest;

import it.polimi.se2018.model.ToolCard;
import it.polimi.se2018.model.toolcards.ToolCard9;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Toolcard9Test {
    @Test
    public void retuturnsTest() {
        ToolCard tooltest = new ToolCard9();

        assertEquals("Riga in Sughero", tooltest.getName());
        assertEquals("Dopo aver scelto un dado, piazzalo in una casella che non sia adiacente a un altro dado. Devi rispettare tutte le restrizioni di piazzamento.", tooltest.getDescription());
        assertEquals(9, tooltest.getNumber());
        assertTrue(tooltest.notAdjacent());
    }
}
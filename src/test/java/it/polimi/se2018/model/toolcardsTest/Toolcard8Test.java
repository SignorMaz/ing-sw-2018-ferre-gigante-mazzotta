package it.polimi.se2018.model.toolcardsTest;

import it.polimi.se2018.model.ToolCard;
import it.polimi.se2018.model.toolcards.ToolCard8;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Toolcard8Test {
    @Test
    public void retuturnsTest() {
        ToolCard tooltest = new ToolCard8();

        assertEquals("Tenaglia a Rotelle", tooltest.getName());
        assertEquals("Dopo il tuo primo turno scegli immediatamente un altro dado. Salta il tuo secondo turno in questo round.", tooltest.getDescription());
        assertEquals(8, tooltest.getNumber());
        assertTrue(tooltest.canPlaceTwoDices());
    }
}
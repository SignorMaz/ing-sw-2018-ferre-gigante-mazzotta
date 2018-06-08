package it.polimi.se2018.model.toolcardsTest;

import it.polimi.se2018.model.ToolCard;
import it.polimi.se2018.model.toolcards.ToolCard7;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Toolcard7Test {
    @Test
    public void retuturnsTest() {
        ToolCard tooltest = new ToolCard7();

        assertEquals("Martelletto", tooltest.getName());
        assertEquals("Tira nuovamente tutti i dadi della Riserva. Questa carta può essera usata solo durante il tuo secondo turno, prima di scegliere il secondo dado.", tooltest.getDescription());
        assertEquals(7, tooltest.getNumber());
        assertTrue(tooltest.canShakeDices());
    }
}
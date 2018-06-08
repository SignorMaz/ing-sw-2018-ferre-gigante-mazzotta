package it.polimi.se2018.model.toolcardsTest;


import it.polimi.se2018.model.ToolCard;
import it.polimi.se2018.model.toolcards.ToolCard10;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Toolcard10Test {
    @Test
    public void retuturnsTest() {
        ToolCard tooltest = new ToolCard10();

        assertEquals("Tampone Diamantato", tooltest.getName());
        assertEquals("Dopo aver scelto un dado, giralo sulla faccia opposta 6 diventa 1, 5 diventa 2, 4 diventa 3 ecc.", tooltest.getDescription());
        assertEquals(10, tooltest.getNumber());
        assertTrue(tooltest.canFlipDice());
    }
}
package it.polimi.se2018.model.toolcardsTest;

import it.polimi.se2018.model.ToolCard;
import it.polimi.se2018.model.toolcards.ToolCard6;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Toolcard6Test {
    @Test
    public void retuturnsTest() {
        ToolCard tooltest = new ToolCard6();

        assertEquals("Pennello per Pasta Salda", tooltest.getName());
        assertEquals("Dopo aver scelto un dado, tira nuovamente quel dado. Se non puoi piazzarlo, riponilo nella Riserva.", tooltest.getDescription());
        assertEquals(6, tooltest.getNumber());
        assertTrue(tooltest.canRethrowDice());
    }
}
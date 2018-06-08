package it.polimi.se2018.model.toolcardsTest;

import it.polimi.se2018.model.ToolCard;
import it.polimi.se2018.model.toolcards.ToolCard1;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Toolcard1Test {
    @Test
    public void retuturnsTest() {
        ToolCard tooltest = new ToolCard1();

        assertEquals("Pinza Sgrossatrice", tooltest.getName());
        assertEquals("Dopo aver scelto un dado, aumenta o diminuisci il valore del dado scelto di 1. Non puoi cambiare un 6 in 1 o un 1 in 6", tooltest.getDescription());
        assertEquals(1, tooltest.getNumber());
        assertEquals(true, tooltest.canChangeDiceValue(2, true));
        assertEquals(false, tooltest.canChangeDiceValue(1, false));
        assertEquals(true, tooltest.canChangeDiceValue(1, true));
        assertEquals(false, tooltest.canChangeDiceValue(6, true));
        assertEquals(true, tooltest.canChangeDiceValue(6, false));

    }
}
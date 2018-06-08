package it.polimi.se2018.model.toolcardsTest;

import it.polimi.se2018.model.ToolCard;
import it.polimi.se2018.model.toolcards.ToolCard11;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Toolcard11Test {
    @Test
    public void retuturnsTest() {
        ToolCard tooltest = new ToolCard11();

        assertEquals("Diluente per Pasta Salda", tooltest.getName());
        assertEquals("Dopo aver scelto un dado, riponilo nel Sacchetto, poi pescane uno dal Sacchetto. Scegli il valore del nuovo dado e piazzalo, rispettando tutte le restrizioni di piazzamento.", tooltest.getDescription());
        assertEquals(11, tooltest.getNumber());
        assertTrue(tooltest.canReplaceDice());
    }
}
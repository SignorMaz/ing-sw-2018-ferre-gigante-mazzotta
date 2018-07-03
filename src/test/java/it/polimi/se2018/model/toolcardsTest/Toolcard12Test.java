package it.polimi.se2018.model.toolcardsTest;

import it.polimi.se2018.model.ToolCard;
import it.polimi.se2018.model.toolcards.ToolCard12;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Toolcard12Test {
    @Test
    public void retuturnsTest() {
        ToolCard tooltest = new ToolCard12();

        assertEquals("Taglierina Manuale", tooltest.getName());
        assertEquals("Muovi fino a due dadi dello stesso colore di un solo dado sul Tracciato dei Round. Devi rispettare tutte le restrizioni di piazzamento.", tooltest.getDescription());
        assertEquals(12, tooltest.getNumber());
        assertTrue(tooltest.canMoveTwoPlacedDicesWithTrack());
    }
}
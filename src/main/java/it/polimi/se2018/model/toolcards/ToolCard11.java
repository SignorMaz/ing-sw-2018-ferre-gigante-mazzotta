package it.polimi.se2018.model.toolcards;

import it.polimi.se2018.model.ToolCard;

public class ToolCard11 extends ToolCard {

    @Override
    public String getName() {
        return "Diluente per Pasta Salda";
    }

    @Override
    public String getDescription() {
        return "Dopo aver scelto un dado, riponilo nel Sacchetto, poi pescane uno dal Sacchetto. Scegli il valore del nuovo dado e piazzalo, rispettando tutte le restrizioni di piazzamento.";
    }

    @Override
    public int getNumber() {
        return 11;
    }
}

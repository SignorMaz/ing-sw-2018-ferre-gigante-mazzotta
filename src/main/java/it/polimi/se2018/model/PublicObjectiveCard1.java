package it.polimi.se2018.model;

public class PublicObjectiveCard1 implements ObjectiveCard {
    @Override
    public String getName() {
        return "Colori diversi - Riga";
    }

    @Override
    public String getDescription() {
        return "Righe senza colori ripetuti";
    }

    @Override
    public int getPoints() {
        return 0;
    }
}

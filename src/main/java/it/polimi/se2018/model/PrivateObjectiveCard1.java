package it.polimi.se2018.model;

public class PrivateObjectiveCard1 implements ObjectiveCard {
    @Override
    public String getName() {
        return "Sfumature rosso";
    }

    @Override
    public String getDescription() {
        return "Somma dei valori su tutti i dadi rossi";
    }

    @Override
    public int getPoints() {
        return 0;
    }
}

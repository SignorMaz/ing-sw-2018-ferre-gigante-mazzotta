package it.polimi.se2018.view.cli.commands;

import it.polimi.se2018.model.ObjectiveCard;
import it.polimi.se2018.view.PlayerView;

public class ListPrivObjCards implements Command {
    @Override
    public String getText() {
        return "List private objective cards";
    }

    @Override
    public void handle(PlayerView view) {
        for (ObjectiveCard card : view.getPlayerViewBase().getPrivateObjectCards()) {
            System.out.println(card.getName() + " - " + card.getDescription());
        }
    }
}
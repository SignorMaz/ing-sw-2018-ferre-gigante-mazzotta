package it.polimi.se2018.view.cli.commands;

import it.polimi.se2018.model.ObjectiveCard;
import it.polimi.se2018.view.cli.PlayerViewCli;

public class ListPubObjCards implements Command {
    @Override
    public String getText() {
        return "List private objective cards";
    }

    @Override
    public void handle(PlayerViewCli view) {
        for (ObjectiveCard card : view.getPlayerViewBase().getPublicObjectCards()) {
            System.out.println(card.getName() + " - " + card.getDescription());
        }
    }
}

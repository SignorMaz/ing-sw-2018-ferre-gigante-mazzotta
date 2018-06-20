package it.polimi.se2018.view.cli.commands;

import it.polimi.se2018.controller.actions.UseToolCardAction;
import it.polimi.se2018.model.ToolCard;
import it.polimi.se2018.view.PlayerView;
import it.polimi.se2018.view.cli.InputHelper;

import java.util.List;
import java.util.Scanner;

public class UseToolCard implements Command {
    @Override
    public String getText() {
        return "Use a ToolCard";
    }

    @Override
    public void handle(PlayerView view) {
        Scanner input = new Scanner(System.in);

        List<ToolCard> cards = view.getPlayerViewBase().getToolCards();
        int chosenCardNum = InputHelper.chooseOption(input, cards,
                card -> card.getName() + " - " + card.getDescription());
        ToolCard toolCard = view.getPlayerViewBase().getToolCards().get(chosenCardNum);

        view.getPlayerViewBase().setToolCard(toolCard);
        view.getPlayerViewBase().send(new UseToolCardAction(toolCard));    }
}

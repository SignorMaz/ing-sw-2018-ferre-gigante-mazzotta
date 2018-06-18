package it.polimi.se2018.view.cli.commands;

import it.polimi.se2018.model.ToolCard;
import it.polimi.se2018.view.PlayerView;

import java.util.Scanner;

public class UseToolCard implements Command {
    @Override
    public String getText() {
        return "Use a ToolCard";
    }

    @Override
    public void handle(PlayerView view) {
        Scanner input = new Scanner(System.in);

        int lastChoiceNum = view.getPlayerViewBase().getToolCards().size() - 1;
        for (int i = 0; i <= lastChoiceNum; i++) {
            ToolCard card = view.getPlayerViewBase().getToolCards().get(i);
            System.out.println(String.format("  %d) %s", i, card.getDescription()));
        }

        int chosenCardNum;
        do {
            System.out.println(String.format("Choose a ToolCard (0-%d):", lastChoiceNum));
            chosenCardNum = input.nextInt();
        } while (chosenCardNum < 0 || chosenCardNum > lastChoiceNum);

        ToolCard toolCard = view.getPlayerViewBase().getToolCards().get(chosenCardNum);
        // TODO: Send Action with toolCard
    }
}

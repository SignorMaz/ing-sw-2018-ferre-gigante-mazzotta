package it.polimi.se2018.view.cli.commands;

import it.polimi.se2018.model.*;
import it.polimi.se2018.view.PlayerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlaceDice implements Command {

    @Override
    public String getText() {
        return "Place dice";
    }

    @Override
    public void handle(PlayerView view) {
        Scanner input = new Scanner(System.in);

        int row;
        for (;;) {
            System.out.println(String.format("Choose a row (0-%d)", WindowPattern.ROWS));
            row = input.nextInt();
            if (row > 0 && row < WindowPattern.ROWS) {
                break;
            }
            System.out.println("Invalid value");
        }

        int column;
        for (;;) {
            System.out.println(String.format("Choose a column (0-%d)", WindowPattern.COLUMNS));
            column = input.nextInt();
            if (column > 0 && column < WindowPattern.COLUMNS) {
                break;
            }
            System.out.println("Invalid value");
        }

        List<Dice> draftPool = new ArrayList<>(); // TODO: getDraftPool() from view
        System.out.println("Dices:");
        int lastChoiceNum = draftPool.size() - 1;
        for (int i = 0; i <= lastChoiceNum; i++) {
            Dice dice = draftPool.get(i);
            String text = String.format("  %d) %d %s", i, dice.getNumber(), dice.getColor());
            System.out.println(text);
        }

        int chosenDiceNum;
        do {
            System.out.println(String.format("Choose a dice (0-%d):", lastChoiceNum));
            chosenDiceNum = input.nextInt();
        } while (chosenDiceNum < 0 || chosenDiceNum > lastChoiceNum);

        Position position = new Position(row, column);
        Dice dice = draftPool.get(chosenDiceNum);
        ToolCard toolcard = null; // TODO: Get toolcard in use

        if (!view.getPlayerViewBase().getWindowFrame().isPositionValid(dice, position, toolcard)) {
            System.out.println("The move is not valid");
        } else {
            view.getPlayerViewBase().send(null);  // TODO: send Action with chosenDice and Position
        }
    }
}

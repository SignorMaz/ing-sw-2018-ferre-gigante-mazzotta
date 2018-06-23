package it.polimi.se2018.view.cli.commands;

import it.polimi.se2018.model.*;
import it.polimi.se2018.view.cli.InputHelper;
import it.polimi.se2018.view.cli.PlayerViewCli;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlaceDice implements Command {

    @Override
    public String getText() {
        return "Place dice";
    }

    @Override
    public void handle(PlayerViewCli view) {
        Scanner input = view.getScanner();

        int row = InputHelper.getInt(input, 1, WindowPattern.ROWS, "Insert the row number");
        int column = InputHelper.getInt(input, 1, WindowPattern.COLUMNS, "Insert the column number");

        // We display numbers starting from 1, but we actually start from 0
        row -= 1;
        column -= 1;

        List<Dice> draftPool = new ArrayList<>(); // TODO: getDraftPool() from view
        System.out.println("Dices:");
        int chosenDiceNum = InputHelper.chooseOption(input, draftPool,
                dice -> dice.getNumber() + " " + dice.getColor().toString().toLowerCase(),
                true);
        if (chosenDiceNum >= draftPool.size()) {
            return;
        }

        Position position = new Position(row, column);
        Dice dice = draftPool.get(chosenDiceNum);
        ToolCard toolcard = null; // TODO: Get toolcard in use

        if (!view.getPlayerViewBase().getWindowFrame().isPositionValid(dice, position, toolcard)) {
            System.out.println("The move is not valid");
        } else {
            view.getPlayerViewBase().send(null);  // TODO: send Action with chosenDice and Position
        }
    }

    @Override
    public boolean canPerform(PlayerViewCli view) {
        return view.isMyTurn();
    }
}


package it.polimi.se2018.view.cli;

import it.polimi.se2018.model.*;

public class WindowFramePrinter {

    private WindowFramePrinter() {
    }

    private static void printCellRow(WindowFrame windowFrame, int row) {
        String missingElementChar = "-";
        System.out.print("│");
        for (int j = 0; j < WindowPattern.COLUMNS; j++) {
            Position curPosition = new Position(row, j);
            WindowCell cell = windowFrame.getWindowPattern().getPattern().get(curPosition);
            if (cell.number == 0) {
                System.out.print(missingElementChar);
            } else {
                System.out.print(cell.number);
            }
            if (cell.color == Color.BLANK) {
                System.out.print(missingElementChar);
            } else {
                System.out.print(cell.color.toString().charAt(0));
            }
            System.out.print("│");
        }
        System.out.println();
    }

    private static void printDiceRow(WindowFrame windowFrame, int row) {
        System.out.print("│");
        for (int j = 0; j < WindowPattern.COLUMNS; j++) {
            Position curPosition = new Position(row, j);
            Dice dice = windowFrame.getPlacedDices().get(curPosition);
            if (dice != null) {
                System.out.print(dice.getNumber());
                System.out.print(dice.getColor().toString().charAt(0));
            } else {
                System.out.print("  ");
            }
            System.out.print("│");
        }
        System.out.println();
    }

    public static void print(WindowFrame windowFrame) {
        /*
         * First row of each cell: WindowCell
         * Second row of each cell: Dice
         *
         *   1  2  3  4  5
         *  ┌──┬──┬──┬──┬──┐
         * 1│4-│--│2-│5-│-G│
         *  │  │  │  │  │  │
         *  ├──┼──┼──┼──┼──┤
         * 1│4-│--│2-│5-│-G│
         *  │  │  │  │  │  │
         *  ├──┼──┼──┼──┼──┤
         * 2│4-│--│2-│5-│-G│
         *  │  │  │  │  │  │
         *  ├──┼──┼──┼──┼──┤
         * 3│4-│--│2-│5-│-G│
         *  │  │  │  │  │  │
         *  └──┴──┴──┴──┴──┘
         */
        String rowNumberFormat = "%4s";

        System.out.println("Map: " + windowFrame.getWindowPattern().getName());
        System.out.println("Difficulty: " + windowFrame.getWindowPattern().getDifficulty());
        System.out.println();

        String lefMargin = String.format(rowNumberFormat, "");

        System.out.print(lefMargin);
        for (int j = 0; j < WindowPattern.COLUMNS; j++) {
            System.out.print(String.format(" %d ", j + 1));
        }
        System.out.println();

        // First row
        System.out.print(String.format("%s┌──┬", lefMargin));
        for (int j = 1; j < WindowPattern.COLUMNS - 1; j++) {
            System.out.print("──┬");
        }
        System.out.println("──┐");

        System.out.print(String.format(rowNumberFormat, 1));
        printCellRow(windowFrame, 0);
        System.out.print(lefMargin);
        printDiceRow(windowFrame, 0);

        for (int i = 1; i < WindowPattern.ROWS; i++) {
            System.out.print(String.format("%s├──┼", lefMargin));
            for (int j = 1; j < WindowPattern.COLUMNS - 1; j++) {
                System.out.print("──┼");
            }
            System.out.println("──┤");

            System.out.print(String.format(rowNumberFormat, i));
            printCellRow(windowFrame, i);
            System.out.print(lefMargin);
            printDiceRow(windowFrame, i);
        }

        // Last row
        System.out.print(String.format("%s└──┴", lefMargin));
        for (int j = 1; j < WindowPattern.COLUMNS - 1; j++) {
            System.out.print("──┴");
        }
        System.out.println("──┘");
    }
}

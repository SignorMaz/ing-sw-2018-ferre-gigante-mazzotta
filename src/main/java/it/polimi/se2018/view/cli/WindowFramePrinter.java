package it.polimi.se2018.view.cli;

import it.polimi.se2018.model.*;

public class WindowFramePrinter {

    private WindowFramePrinter() {
    }

    private static String cellRowToString(WindowFrame windowFrame, int row) {
        StringBuilder sb = new StringBuilder();
        String missingElementChar = "-";
        sb.append("│");
        for (int j = 0; j < WindowPattern.COLUMNS; j++) {
            Position curPosition = new Position(row, j);
            WindowCell cell = windowFrame.getWindowPattern().getPattern().get(curPosition);
            if (cell.number == 0) {
                sb.append(missingElementChar);
            } else {
                sb.append(cell.number);
            }
            if (cell.color == Color.BLANK) {
                sb.append(missingElementChar);
            } else {
                sb.append(cell.color.toString().charAt(0));
            }
            sb.append("│");
        }
        sb.append("\n");

        return sb.toString();
    }

    private static String diceRowToString(WindowFrame windowFrame, int row) {
        StringBuilder sb = new StringBuilder();
        sb.append("│");
        for (int j = 0; j < WindowPattern.COLUMNS; j++) {
            Position curPosition = new Position(row, j);
            Dice dice = windowFrame.getPlacedDices().get(curPosition);
            if (dice != null) {
                sb.append(dice.getNumber());
                sb.append(dice.getColor().toString().charAt(0));
            } else {
                sb.append("  ");
            }
            sb.append("│");
        }
        sb.append("\n");

        return sb.toString();
    }

    public static void print(WindowFrame windowFrame) {
        System.out.print(frameToString(windowFrame));
    }

    public static String frameToString(WindowFrame windowFrame) {
        StringBuilder sb = new StringBuilder();

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

        sb.append("Map: ");
        sb.append(windowFrame.getWindowPattern().getName());
        sb.append("\n");
        sb.append("Difficulty: ");
        sb.append(windowFrame.getWindowPattern().getDifficulty());
        sb.append("\n\n");

        String lefMargin = String.format(rowNumberFormat, "");

        sb.append(lefMargin);
        for (int j = 0; j < WindowPattern.COLUMNS; j++) {
            sb.append(String.format(" %d ", j + 1));
        }
        sb.append("\n");

        // First row
        sb.append(String.format("%s┌──┬", lefMargin));
        for (int j = 1; j < WindowPattern.COLUMNS - 1; j++) {
            sb.append("──┬");
        }
        sb.append("──┐");
        sb.append("\n");

        sb.append(String.format(rowNumberFormat, 1));
        sb.append(cellRowToString(windowFrame, 0));
        sb.append(lefMargin);
        sb.append(diceRowToString(windowFrame, 0));

        for (int i = 1; i < WindowPattern.ROWS; i++) {
            sb.append(String.format("%s├──┼", lefMargin));
            for (int j = 1; j < WindowPattern.COLUMNS - 1; j++) {
                sb.append("──┼");
            }
            sb.append("──┤");
            sb.append("\n");

            sb.append(String.format(rowNumberFormat, i));
            sb.append(cellRowToString(windowFrame, i));
            sb.append(lefMargin);
            sb.append(diceRowToString(windowFrame, i));
        }

        // Last row
        sb.append(String.format("%s└──┴", lefMargin));
        for (int j = 1; j < WindowPattern.COLUMNS - 1; j++) {
            sb.append("──┴");
        }
        sb.append("──┘");
        sb.append("\n");

        return sb.toString();
    }
}

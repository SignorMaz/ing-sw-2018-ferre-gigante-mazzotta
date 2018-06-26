package it.polimi.se2018.view.cli;

import it.polimi.se2018.model.*;

import java.util.List;
import java.util.Scanner;

public class InputHelper {
    private InputHelper() {}

    /**
     * Request an integer value to the user
     *
     * @param scanner the scanner from where we read the int
     * @param min the minimum value accepted (inclusive)
     * @param max the maximum value accepted (inclusive)
     * @return the value read
     */
    public static int getInt(Scanner scanner, int min, int max) {
        return getInt(scanner, min, max, "Choose");
    }

    /**
     * Request an integer value to the user
     *
     * @param scanner the scanner from where we read the int
     * @param min the minimum value accepted (inclusive)
     * @param max the maximum value accepted (inclusive)
     * @param message the string shown to the user, followed by the accepted range
     * @return the value read
     */
    public static int getInt(Scanner scanner, int min, int max, String message) {
        while (true) {
            System.out.print(String.format("%s (%d-%d): ", message, min, max));
            if (!scanner.hasNextInt()) {
                scanner.next();
                continue;
            }
            int value = scanner.nextInt();
            if (value >= min && value <= max) {
                return value;
            }
        }
    }

    public interface OptionString<T> {
        /**
         * Method that converts a given option to a String.
         *
         * @param option the option to represent
         * @return the representation of the option
         */
        String convert(T option);
    }

    /**
     * Print a list of option and ask to choose one
     *
     * @param scanner the scanner from where we read the chosen option
     * @param options a list of options
     * @param converter an object to convert each option to a string.
     *                  Pass null to use the options' toString()
     * @param canCancel true if there should be an option to cancel the operation
     * @return the position in the list of the chosen element. If canCancel is
     *         true, the position will be options.size().
     */
    public static <T> int chooseOption(Scanner scanner, List<T> options, OptionString<T> converter,
                                       boolean canCancel) {
        int num = 0;
        for (T option : options) {
            num++;
            String optionString = converter != null ?
                    converter.convert(option) : option.toString();
            String text = String.format(" %d) %s", num, optionString);
            System.out.println(text);
        }

        if (canCancel) {
            num++;
            String text = String.format(" %d) Cancel", num);
            System.out.println(text);
        }

        int chosenOption = getInt(scanner, 1, num);
        // The list starts from 0, the options from 1
        return chosenOption - 1;
    }

    /**
     * Print the WindowPattern and ask to insert a valid position
     *
     * @param scanner the scanner from where we read the user input
     * @param windowFrame the WindowFrame to print
     * @return the chosen position
     */
    public static Position choosePosition(Scanner scanner, WindowFrame windowFrame) {
        if (windowFrame != null) {
            WindowFramePrinter.print(windowFrame);
        }

        int row = InputHelper.getInt(scanner, 1, WindowPattern.ROWS, "Insert the row number");
        int column = InputHelper.getInt(scanner, 1, WindowPattern.COLUMNS, "Insert the column number");

        // We display numbers starting from 1, but we actually start from 0
        row -= 1;
        column -= 1;

        return new Position(row, column);
    }

    /**
     * Ask to insert a valid position
     *
     * @param scanner the scanner from where we read the user input
     * @return the chosen position
     */
    public static Position choosePosition(Scanner scanner) {
        return choosePosition(scanner, null);
    }

    /**
     * Ask to choose a dice from the draft pool
     *
     * @param scanner the scanner from where we read the user input
     * @param draftPool the draft pool
     * @return the chosen dice or null if the user aborted the operation
     */
    public static Dice chooseDraftPoolDice(Scanner scanner, List<Dice> draftPool) {
        int chosenDiceNum = InputHelper.chooseOption(scanner, draftPool,
                dice -> dice.getNumber() + " " + dice.getColor().toString().toLowerCase(),
                true);
        if (chosenDiceNum >= draftPool.size()) {
            return null;
        }

        return draftPool.get(chosenDiceNum);
    }

}

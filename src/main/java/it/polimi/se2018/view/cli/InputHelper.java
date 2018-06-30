package it.polimi.se2018.view.cli;

import it.polimi.se2018.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputHelper {
    private InputHelper() {
    }

    /**
     * Request an integer value to the user
     *
     * @param scanner the scanner from where we read the int
     * @param min     the minimum value accepted (inclusive)
     * @param max     the maximum value accepted (inclusive)
     * @return the value read
     */
    public static InputResponse<Integer> getInt(Scanner scanner, int min, int max) {
        return getInt(scanner, min, max, "Choose");
    }

    /**
     * Request an integer value to the user
     *
     * @param scanner the scanner from where we read the int
     * @param min     the minimum value accepted (inclusive)
     * @param max     the maximum value accepted (inclusive)
     * @param message the string shown to the user, followed by the accepted range
     * @return the value read
     */
    public static InputResponse<Integer> getInt(Scanner scanner, int min, int max, String message) {
        System.out.print(String.format("%s (%d-%d): ", message, min, max));
        String response = scanner.nextLine();
        int value;
        try {
            value = Integer.parseInt(response);
        } catch (NumberFormatException ignored) {
            return new InputResponse<>(0, false);
        }
        if (value >= min && value <= max) {
            return new InputResponse<>(value, true);
        }
        return new InputResponse<>(0, false);
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
     * @param scanner   the scanner from where we read the chosen option
     * @param options   a list of options
     * @param converter an object to convert each option to a string.
     *                  Pass null to use the options' toString()
     * @return the position in the list of the chosen element. If canCancel is
     * true, the response will not be valid.
     */
    public static <T> InputResponse<Integer> chooseOption(Scanner scanner, List<T> options, OptionString<T> converter) {
        int num = 0;
        for (T option : options) {
            num++;
            String optionString = converter != null ?
                    converter.convert(option) : option.toString();
            String text = String.format(" %d) %s", num, optionString);
            System.out.println(text);
        }

        InputResponse<Integer> response = getInt(scanner, 1, num);
        if (!response.isValid()) {
            return response;
        }

        // The list starts from 0, the options from 1
        return new InputResponse<>(response.getValue() - 1, true);
    }

    /**
     * Print the WindowPattern and ask to insert a valid position
     *
     * @param scanner     the scanner from where we read the user input
     * @param windowFrame the WindowFrame to print
     * @return the chosen position
     */
    public static InputResponse<Position> choosePosition(Scanner scanner, WindowFrame windowFrame) {
        if (windowFrame != null) {
            WindowFramePrinter.print(windowFrame);
        }

        InputResponse<Integer> rowResponse = InputHelper.getInt(scanner, 1, WindowPattern.ROWS, "Insert the row number");
        if (!rowResponse.isValid()) {
            return new InputResponse<>(null, false);
        }
        InputResponse<Integer> colResponse = InputHelper.getInt(scanner, 1, WindowPattern.COLUMNS, "Insert the column number");
        if (!colResponse.isValid()) {
            return new InputResponse<>(null, false);
        }

        // We display numbers starting from 1, but we actually start from 0
        int row = rowResponse.getValue() - 1;
        int column = colResponse.getValue() - 1;

        return new InputResponse<>(new Position(row, column), true);
    }

    /**
     * Ask to insert a valid position
     *
     * @param scanner the scanner from where we read the user input
     * @return the chosen position
     */
    public static InputResponse<Position> choosePosition(Scanner scanner) {
        return choosePosition(scanner, null);
    }

    /**
     * Ask to choose a dice from the draft pool
     *
     * @param scanner   the scanner from where we read the user input
     * @param draftPool the draft pool
     * @return the chosen dice or null if the user aborted the operation
     */
    public static InputResponse<Dice> chooseDraftPoolDice(Scanner scanner, List<Dice> draftPool) {
        InputResponse<Integer> response;
        response = InputHelper.chooseOption(scanner, draftPool,
                dice -> dice.getNumber() + " " + dice.getColor().toString().toLowerCase());
        if (response.isValid()) {
            return new InputResponse<>(null, false);
        }

        return new InputResponse<>(draftPool.get(response.getValue()), true);
    }

    /**
     * Ask for yes or no.
     *
     * @param scanner the scanner from where we read the user input
     * @return true for yes, false for no
     */
    public static InputResponse<Boolean> yesOrNo(Scanner scanner) {
        List<String> options = new ArrayList<>();
        String yes = "Yes";
        options.add(yes);
        options.add("No");
        InputResponse<Integer> response = InputHelper.chooseOption(scanner, options, null);
        boolean answeredYes = yes.equals(options.get(response.getValue()));
        return new InputResponse<>(answeredYes, response.isValid());
    }

}

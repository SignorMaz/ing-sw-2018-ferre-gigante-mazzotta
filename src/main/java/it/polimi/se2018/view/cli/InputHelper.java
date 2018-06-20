package it.polimi.se2018.view.cli;

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
     * @return the position in the list of the chosen element. If canCancel is
     *         true, the position will be out of the list range.
     */
    public static <T> int chooseOption(Scanner scanner, List<T> options, OptionString<T> converter) {
        int num = 0;
        for (T option : options) {
            num++;
            String optionString = converter != null ?
                    converter.convert(option) : option.toString();
            String text = String.format(" %d) %s", num, optionString);
            System.out.println(text);
        }

        int chosenOption = getInt(scanner, 1, num);
        // The list starts from 0, the options from 1
        return chosenOption - 1;
    }
}

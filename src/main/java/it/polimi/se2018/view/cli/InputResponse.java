package it.polimi.se2018.view.cli;

/**
 * Class that holds a user input response.
 *
 * @param <T> the type of the response value
 */
public class InputResponse<T> {
    private final T value;
    private final boolean valid;

    public InputResponse(T value, boolean valid) {
        this.value = value;
        this.valid = valid;
    }

    /**
     * Get the value of this response. This can be null if {@link #isValid()} is false.
     *
     * @return the value of the response
     */
    public T getValue() {
        return value;
    }

    /**
     * @return whether this response is valid or not
     */
    public boolean isValid() {
        return valid;
    }
}

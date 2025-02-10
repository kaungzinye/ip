package gojosatoru.exceptions;

/**
 * Represents an exception thrown when an invalid date is encountered.
 */
public class InvalidDateException extends GojoException {
    private static final String DEFAULT_MESSAGE = "   ____________________________________________________________\n  "
        + "The date provided is invalid or incorrectly formatted. Please check and try again.\n"
        + "   ____________________________________________________________";

    /**
     * Constructs an InvalidDateException with a default error message.
     */
    public InvalidDateException() {
        super(DEFAULT_MESSAGE);
    }

    /**
     * Constructs an InvalidDateException with a custom error message.
     *
     * @param message the custom error message
     */
    public InvalidDateException(String message) {
        super(message);
    }

    /**
     * Returns the error message without the lines for GUI display.
     *
     * @return the error message without the lines
     */
    public String getMessageForGui() {
        return "The date provided is invalid or incorrectly formatted. Please check and try again.";
    }
}

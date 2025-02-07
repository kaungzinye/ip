package gojosatoru.exceptions;

/**
 * Represents an exception thrown when an invalid date is encountered.
 */
public class InvalidDateException extends GojoException {
    /**
     * Constructs an InvalidDateException with a default error message.
     */
    public InvalidDateException() {
        super("   ____________________________________________________________\n  "
            + "The date provided is invalid or incorrectly formatted. Please check and try again.\n"
            + "   ____________________________________________________________");
    }

    /**
     * Constructs an InvalidDateException with a custom error message.
     *
     * @param message the custom error message
     */
    public InvalidDateException(String message) {
        super(message); // Allow custom error messages
    }
}

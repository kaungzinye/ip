package gojosatoru.exceptions;

/**
 * Represents a generic exception in the Gojo application.
 */
public class GojoException extends Exception {
    /**
     * Constructs a GojoException with a default error message.
     */
    public GojoException() {
        super("An error occurred in Gojo.");
    }

    /**
     * Constructs a GojoException with a custom error message.
     *
     * @param message the custom error message
     */
    public GojoException(String message) {
        super(message);
    }
}

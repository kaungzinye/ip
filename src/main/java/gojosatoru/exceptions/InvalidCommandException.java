package gojosatoru.exceptions;

/**
 * Represents an exception thrown when an invalid command is encountered.
 */
public class InvalidCommandException extends GojoException {
    private static final String DEFAULT_MESSAGE = "   ____________________________________________________________\n  "
        + " Oi... I don't know what that means, didn't teach ya that in Jujutsu High..\n"
        + "   ____________________________________________________________";

    /**
     * Constructs an InvalidCommandException with a default error message.
     */
    public InvalidCommandException() {
        super(DEFAULT_MESSAGE);
    }

    /**
     * Constructs an InvalidCommandException with a custom error message.
     *
     * @param message the custom error message
     */
    public InvalidCommandException(String message) {
        super(message);
    }

    /**
     * Returns the error message without the lines for GUI display.
     *
     * @return the error message without the lines
     */
    public String getMessageForGui() {
        return "Oi... I don't know what that means, didn't teach ya that in Jujutsu High..";
    }
}

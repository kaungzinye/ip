package gojosatoru.exceptions;

/**
 * Represents an exception thrown when a required argument is missing.
 */
public class MissingArgumentException extends GojoException {
    private static final String DEFAULT_MESSAGE = "   ____________________________________________________________\n  "
        + " Even with my Six Eyes, I can't tell what the name of your task is... "
        + "BECAUSE IT'S EMPTY! WRITE IT AGAIN IDIOT!\n"
        + "   ____________________________________________________________";
    private static final String DEFAULT_GUI_MESSAGE = "Even with my Six Eyes, "
        + "I can't tell what the name of your task is... "
        + "BECAUSE IT'S EMPTY! WRITE IT AGAIN IDIOT!";
    private final String guiMessage;
    /**
     * Constructs a MissingArgumentException with a default error message.
     */
    public MissingArgumentException() {
        super(DEFAULT_MESSAGE);
        this.guiMessage = DEFAULT_GUI_MESSAGE;
    }

    /**
     * Constructs a MissingArgumentException with a custom error message.
     *
     * @param message the custom error message
     */
    public MissingArgumentException(String message) {
        super(message);
        this.guiMessage = message;
    }

    /**
     * Returns the error message without the lines for GUI display.
     *
     * @return the error message without the lines
     */
    public String getMessageForGui() {
        return this.guiMessage;
    }
}

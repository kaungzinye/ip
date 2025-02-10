package gojosatoru.exceptions;

/**
 * Represents an exception thrown when a task is not found.
 */
public class TaskNotFoundException extends GojoException {
    private static final String DEFAULT_MESSAGE = "   ____________________________________________________________\n   "
        + "My Six Eyes can't find your task, because it doesn't exist you idiot.\n   "
        + "Not surprised since I'm stronger than you..\n   "
        + "Try again...\n"
        + "   ____________________________________________________________";

    /**
     * Constructs a TaskNotFoundException with a default error message.
     */
    public TaskNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    /**
     * Constructs a TaskNotFoundException with a custom error message.
     *
     * @param message the custom error message
     */
    public TaskNotFoundException(String message) {
        super(message);
    }

    /**
     * Returns the error message without the lines for GUI display.
     *
     * @return the error message without the lines
     */
    public String getMessageForGui() {
        return "My Six Eyes can't find your task, because it doesn't exist you idiot. "
            + "Not surprised since I'm stronger than you.. Try again...";
    }
}

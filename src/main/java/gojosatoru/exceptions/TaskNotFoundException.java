package gojosatoru.exceptions;

/**
 * Represents an exception thrown when a task is not found.
 */
public class TaskNotFoundException extends GojoException {
    /**
     * Constructs a TaskNotFoundException with a default error message.
     */
    public TaskNotFoundException() {
        super("   ____________________________________________________________\n   "
            + "My Six Eyes I can't find your task, because you it doesn't exist you idiot.\n   "
            + "Not surprised since I'm stronger than you..\n   "
            + "Try again...\n"
            + "   ____________________________________________________________");
    }

    /**
     * Constructs a TaskNotFoundException with a custom error message.
     *
     * @param message the custom error message
     */
    public TaskNotFoundException(String message) {
        super(message);
    }
}

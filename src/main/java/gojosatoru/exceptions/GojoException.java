package gojosatoru.exceptions;

/**
 * Represents a general exception in the Gojo Satoru application.
 */
public class GojoException extends Exception {
    /**
     * Constructs a GojoException with a custom error message.
     *
     * @param message the custom error message
     */
    public GojoException(String message) {
        super(message);
    }

    /**
     * Returns the error message without the lines for GUI display.
     *
     * @return the error message without the lines
     */
    public String getMessageForGui() {
        return getMessage().replaceAll("   ____________________________________________________________\n  ", "").replaceAll("\n   ____________________________________________________________", "");
    }
}

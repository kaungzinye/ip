package gojosatoru.exceptions;

/**
 * Represents an exception thrown when an invalid command is encountered.
 */
public class InvalidCommandException extends GojoException {

  /**
   * Constructs an InvalidCommandException with a default error message.
   */
  public InvalidCommandException() {
    super("   ____________________________________________________________\n  " +
        " Oi... I don't know what that means, didn't teach ya that in Jujutsu High..\n" +
        "   ____________________________________________________________");
  }

  /**
   * Constructs an InvalidCommandException with a custom error message.
   *
   * @param message the custom error message
   */
  public InvalidCommandException(String message) {
    super(message);
  }
}

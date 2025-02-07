package gojosatoru.exceptions;

/**
 * Represents an exception thrown when a required argument is missing.
 */
public class MissingArgumentException extends GojoException {

  /**
   * Constructs a MissingArgumentException with a default error message.
   */
  public MissingArgumentException() {
    super("   ____________________________________________________________\n  " +
        " Even with my Six Eyes, I can't tell what the name of your task is... BECAUSE IT'S EMPTY! WRITE IT AGAIN IDIOT!\n" +
        "   ____________________________________________________________");
  }

  /**
   * Constructs a MissingArgumentException with a custom error message.
   *
   * @param message the custom error message
   */
  public MissingArgumentException(String message) {
    super(message);
  }
}

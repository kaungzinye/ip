package gojosatoru.exceptions;

public class InvalidDateException extends GojoException {
  public InvalidDateException() {
    super("   ____________________________________________________________\n  " +
        "The date provided is invalid or incorrectly formatted. Please check and try again.\n" +
        "   ____________________________________________________________\n");
  }

  public InvalidDateException(String message) {
    super(message); // Allow custom error messages
  }
}
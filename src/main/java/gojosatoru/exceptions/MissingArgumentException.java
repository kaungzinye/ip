package gojosatoru.exceptions;

public class MissingArgumentException extends GojoException {
  public MissingArgumentException() {
    super("   ____________________________________________________________\n  " +
        " Even with my Six Eyes, I can't tell what the name of your task is... BECAUSE IT'S EMPTY! WRITE IT AGAIN IDIOT!\n" +
        "   ____________________________________________________________");
  }

  public MissingArgumentException(String message) {
    super(message);
  }
}

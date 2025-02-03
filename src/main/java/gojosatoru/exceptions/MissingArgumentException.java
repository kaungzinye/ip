package main.exceptions;

import main.GojoSatoru;

public class MissingArgumentException extends GojoException {
  public MissingArgumentException() {
    super("   ____________________________________________________________\n  " +
        "Even with my Six Eyes, I can't tell what the name of your task is... BECAUSE IT'S EMPTY! WRITE IT AGAIN IDIOT!\n" +
        "   ____________________________________________________________\n");
  }

  public MissingArgumentException(String message) {
    super(message);
  }
}

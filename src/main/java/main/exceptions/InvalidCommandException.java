package main.exceptions;

import main.GojoSatoru;

public class InvalidCommandException extends GojoException {
  public InvalidCommandException() {
    super("   ____________________________________________________________\n  " +
        "Oi... I don't know what that means, didn't teach ya that in Jujutsu High.. \n" +
        "   ____________________________________________________________\n");
  }

  public InvalidCommandException(String message) {
    super(message); // Allow custom error messages
  }
}

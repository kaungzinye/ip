package gojosatoru.exceptions;

public class TaskNotFoundException extends GojoException {
  public TaskNotFoundException() {
    super("   ____________________________________________________________\n   " +
        "My Six Eyes I can't find your task, because you it doesn't exist you idiot.\n   Not surprised since I'm stronger than you..\n   Try again...\n" +
        "   ____________________________________________________________");
  }

  public TaskNotFoundException(String message) {
    super(message);
  }
}

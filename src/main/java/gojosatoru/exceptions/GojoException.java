package gojosatoru.exceptions;

import gojosatoru.ui.Ui;

public class GojoException extends Exception {
  private static final Ui UI = new Ui();
  // Default constructor
  public GojoException() {
    super("An error occurred in Gojo.");
  }

  // Constructor with a custom error message
  public GojoException(String message) {
    super(message);
  }
}


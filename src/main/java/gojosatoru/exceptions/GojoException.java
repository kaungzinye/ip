package main.exceptions;

import main.GojoSatoru;

public class GojoException extends Exception {
  // Default constructor
  public GojoException() {
    super("An error occurred in Gojo.");
  }

  // Constructor with a custom error message
  public GojoException(String message) {
    super(message);
  }
}


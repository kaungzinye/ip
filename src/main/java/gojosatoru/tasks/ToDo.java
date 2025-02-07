package gojosatoru.tasks;

import java.time.format.DateTimeFormatter;

/**
 * Represents a to-do task with a description.
 */
public class ToDo extends Task {
  /**
   * Constructs a ToDo task with the specified description and output formatter.
   *
   * @param input the description of the to-do task
   * @param outputFormatter the formatter for displaying the date and time
   */
  public ToDo(String input, DateTimeFormatter outputFormatter) {
    super(input, outputFormatter);
  }

  /**
   * Displays the task in a readable format.
   *
   * @return the formatted task string
   */
  @Override
  public String showTask() {
    return (completed ? "[D][X] " : "[D][ ] ") + taskDescription;
  }

  /**
   * Converts the task to a save format.
   *
   * @return the formatted string for saving
   */
  @Override
  public String toSaveFormat() {
    return "T | " + (completed ? "1" : "0") + " | " + taskDescription;
  }
}
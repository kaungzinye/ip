package gojosatoru.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
  private LocalDateTime by;
  public Deadline(String input, DateTimeFormatter outputFormatter, LocalDateTime by) {
    super(input, outputFormatter);
    this.by = by;
  }
  @Override
  public String showTask(){
    String output;
    if(this.completed) {
      output = "[D][X] " + taskDescription + " (by: " + outputFormatter.format(by) + ")";
    }
    else{
      output = "[D][ ] " + taskDescription + " (by: " + outputFormatter.format(by) + ")";
    }
    return output;
  }

  @Override
  public String toSaveFormat() {
    return "D | " + (completed ? "1" : "0") + " | " + taskDescription + " | " + outputFormatter.format(by);
  }
}

package main.tasks;

public class Deadline extends Task {
  private String by;
  public Deadline(String input, String by) {
    super(input);
    this.by = by;
  }
  @Override
  public String showTask(){
    String output;
    if(this.completed) {
      output = "[D][X] " + taskDescription;
    }
    else{
      output = "[D][ ] " + taskDescription;
    }
    return output;
  }

  @Override
  public String toSaveFormat() {
    return "D | " + (completed ? "1" : "0") + " | " + taskDescription + " | " + by;
  }
}

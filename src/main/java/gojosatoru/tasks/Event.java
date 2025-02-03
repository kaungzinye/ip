package main.tasks;

public class Event extends Task {
  private String from;
  private String to;
  public Event(String description, String from, String to) {
    super(description);
    this.from = from;
    this.to = to;
  }
  @Override
  public String showTask(){
    String output;
    if(this.completed) {
      output = "[E][X] " + taskDescription;
    }
    else{
      output = "[E][ ] " + taskDescription;
    }
    return output;
  }

  @Override
  public String toSaveFormat() {
    return "E | " + (completed ? "1" : "0") + " | " + taskDescription + " | " + from + " | " + to;
  }
}

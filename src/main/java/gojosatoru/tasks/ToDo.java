package main.tasks;

public class ToDo extends Task {
  public ToDo(String input) {
    super(input);
  }
  @Override
  public String showTask(){
    String output;
    if(this.completed) {
      output = "[T][X] " + taskDescription;
    }
    else{
      output = "[T][ ] " + taskDescription;
    }
    return output;
  }

  @Override
  public String toSaveFormat() {
    return "T | " + (completed ? "1" : "0") + " | " + taskDescription;
  }
}

package gojosatoru.tasks;

import java.time.format.DateTimeFormatter;

public abstract class Task {
  protected String taskDescription;
  protected Boolean completed;

  protected DateTimeFormatter outputFormatter;

  public Task(String taskDescription , DateTimeFormatter outputFormatter) {
    this.taskDescription = taskDescription;
    this.completed = false;
    this.outputFormatter = outputFormatter;
  }

  public String getTaskDescription() {
    return taskDescription;
  }

  public void markTask(){
    this.completed = true;
  }

  public void unmarkTask() {
    this.completed = false;
  }

  public Boolean isCompleted() {
    return completed;
  }

  public abstract String showTask();
  // Method to be implemented to save the task in a specific format
  public abstract String toSaveFormat();
}


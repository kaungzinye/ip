package main.tasks;

import main.GojoSatoru;

public abstract class Task {
  protected String taskDescription;
  protected Boolean completed;

  public Task(String taskDescription){
    this.taskDescription = taskDescription;
    this.completed = false;
  }

  public void markTask(){
    this.completed = true;
  }

  public void unmarkTask() {
    this.completed = false;
  }

  public abstract String showTask();
  // Method to be implemented to save the task in a specific format
  public abstract String toSaveFormat();
}


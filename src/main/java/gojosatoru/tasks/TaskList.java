package gojosatoru.tasks;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
  private ArrayList<Task> tasks;

  public TaskList() {
    this.tasks = new ArrayList<>();
  }

  public TaskList(ArrayList<Task> tasks) {
    this.tasks = tasks;
  }

  public ArrayList<Task> getTasks() {
    return tasks;
  }

  public void addTask(Task task) {
    tasks.add(task);
  }

  public void deleteTask(int index) {
    tasks.remove(index);
  }

  public Task getTask(int index) {
    return tasks.get(index);
  }
    public ArrayList<Task> findTasks(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.showTask().contains(keyword)) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }
  public int size() {
    return tasks.size();
  }

  public void clear() {
    tasks.clear();
  }
}
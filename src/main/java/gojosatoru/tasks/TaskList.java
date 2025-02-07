package gojosatoru.tasks;

import java.util.ArrayList;

/**
 * Represents a list of tasks.
 */
public class TaskList {
  private ArrayList<Task> tasks;

  /**
   * Constructs an empty TaskList.
   */
  public TaskList() {
    this.tasks = new ArrayList<>();
  }

  /**
   * Constructs a TaskList with the specified list of tasks.
   *
   * @param tasks the list of tasks
   */
  public TaskList(ArrayList<Task> tasks) {
    this.tasks = tasks;
  }

  /**
   * Gets the list of tasks.
   *
   * @return the list of tasks
   */
  public ArrayList<Task> getTasks() {
    return tasks;
  }

  /**
   * Adds a task to the list.
   *
   * @param task the task to add
   */
  public void addTask(Task task) {
    tasks.add(task);
  }

  /**
   * Deletes a task from the list by index.
   *
   * @param index the index of the task to delete
   */
  public void deleteTask(int index) {
    tasks.remove(index);
  }

  /**
   * Gets a task from the list by index.
   *
   * @param index the index of the task to get
   * @return the task at the specified index
   */
  public Task getTask(int index) {
    return tasks.get(index);
  }

  /**
   * Gets the number of tasks in the list.
   *
   * @return the number of tasks
   */
  public int size() {
    return tasks.size();
  }

  /**
   * Clears all tasks from the list.
   */
  public void clear() {
    tasks.clear();
  }
}
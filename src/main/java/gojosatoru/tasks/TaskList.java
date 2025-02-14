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

    public Task getTask(int index) {
        return tasks.get(index);
    }
    /**
     * Finds tasks that contain the specified keyword in their description.
     *
     * @param keywords the keyword to search for in the task descriptions
     * @return a list of tasks that match the keyword
     */
    public ArrayList<Task> findTasks(String... keywords) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            for (String keyword : keywords) {
                if (task.showTask().contains(keyword)) {
                    matchingTasks.add(task);
                    break;
                }
            }
        }
        return matchingTasks;
    }

    public int size() {
        return tasks.size();
    }
    /**
     * Checks if the list contains a task with the specified description.
     *
     * @param newTaskName the description of the task to check for
     * @return true if the list contains a task with the specified description, false otherwise
     */
    public boolean hasDuplicate(String newTaskName) {
        for (Task task : tasks) {
            if (task.getTaskDescription().equals(newTaskName)) {
                return true;
            }
        }
        return false;
    }
}

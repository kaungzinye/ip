package gojosatoru.tasks;

import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a description and completion status.
 */
public abstract class Task {
    protected String taskDescription;
    protected Boolean completed;
    protected DateTimeFormatter outputFormatter;

    /**
     * Constructs a Task with the specified description and output formatter.
     *
     * @param taskDescription the description of the task
     * @param outputFormatter the formatter for displaying the date and time
     */
    public Task(String taskDescription, DateTimeFormatter outputFormatter) {
        this.taskDescription = taskDescription;
        this.completed = false;
        this.outputFormatter = outputFormatter;
    }

    /**
     * Gets the description of the task.
     *
     * @return the task description
     */
    public String getTaskDescription() {
        return taskDescription;
    }

    /**
     * Marks the task as completed.
     */
    public void markTask() {
        this.completed = true;
    }

    /**
     * Unmarks the task as not completed.
     */
    public void unmarkTask() {
        this.completed = false;
    }

    /**
     * Checks if the task is completed.
     *
     * @return true if the task is completed, false otherwise
     */
    public Boolean isCompleted() {
        return completed;
    }

    /**
     * Displays the task in a readable format.
     *
     * @return the formatted task string
     */
    public abstract String showTask();

    // Method to be implemented to save the task in a specific format

    /**
     * Converts the task to a save format.
     *
     * @return the formatted string for saving
     */
    public abstract String toSaveFormat();
}

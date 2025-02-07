package gojosatoru.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a deadline.
 */
public class Deadline extends Task {

    private LocalDateTime by;

    /**
     * Constructs a Deadline task.
     *
     * @param input           the description of the task
     * @param outputFormatter the formatter for displaying dates
     * @param by              the deadline of the task
     */
    public Deadline(String input, DateTimeFormatter outputFormatter, LocalDateTime by) {
        super(input, outputFormatter);
        this.by = by;
    }

    /**
     * Returns a string representation of the task.
     *
     * @return the string representation of the task
     */
    @Override
    public String showTask() {
        return (completed ? "[D][X] " : "[D][ ] ")
            + taskDescription + " (by: " + outputFormatter.format(by) + ")";
    }

    /**
     * Returns the task in a format suitable for saving.
     *
     * @return the save format of the task
     */
    @Override
    public String toSaveFormat() {
        return "D | " + (completed ? "1" : "0") + " | "
            + taskDescription + " | " + outputFormatter.format(by);
    }
}

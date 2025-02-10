package gojosatoru.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task with a start and end time.
 */
public class Event extends Task {
    private LocalDateTime from;
    private LocalDateTime to;

    /**
     * Constructs an Event with the specified description, output formatter, start time, and end time.
     *
     * @param description the description of the event
     * @param outputFormatter the formatter for displaying the date and time
     * @param from the start time of the event
     * @param to the end time of the event
     */
    public Event(String description, DateTimeFormatter outputFormatter, LocalDateTime from, LocalDateTime to) {
        super(description, outputFormatter);
        this.from = from;
        this.to = to;
    }

    /**
     * Displays the task in a readable format.
     *
     * @return the formatted task string
     */
    @Override
    public String showTask() {
        return (completed ? "[E][X] " : "[E][ ] ") + " (from: "
            + outputFormatter.format(from) + " to: " + outputFormatter.format(to) + ")";
    }

    /**
     * Converts the task to a save format.
     *
     * @return the formatted string for saving
     */
    @Override
    public String toSaveFormat() {
        return "E | " + (completed ? "1" : "0") + " | " + taskDescription
            + " | " + outputFormatter.format(from) + " | " + outputFormatter.format(to);
    }
}

package gojosatoru.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private LocalDateTime from;
    private LocalDateTime to;

    public Event(String description, DateTimeFormatter outputFormatter, LocalDateTime from, LocalDateTime to) {
        super(description, outputFormatter);
        this.from = from;
        this.to = to;
    }

    @Override
    public String showTask() {
        String output;
        if (this.completed) {
            output = "[E][X] " + taskDescription + " (from: " + outputFormatter.format(from) + " to: "
                + outputFormatter.format(to) + ")";
        } else {
            output = "[E][ ] " + taskDescription + " (from: " + outputFormatter.format(from) + " to: "
                + outputFormatter.format(to) + ")";
        }
        return output;
    }

    @Override
    public String toSaveFormat() {
        return "E | " + (completed ? "1" : "0") + " | " + taskDescription + " | "
            + outputFormatter.format(from) + " | " + outputFormatter.format(to);
    }
}

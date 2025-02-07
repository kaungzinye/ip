package gojosatoru.handlers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

import gojosatoru.exceptions.InvalidDateException;
import gojosatoru.exceptions.MissingArgumentException;
import gojosatoru.tasks.Deadline;
import gojosatoru.tasks.Event;
import gojosatoru.tasks.ToDo;

/**
 * Handles the creation and management of tasks.
 */
public class TaskHandler {
    private DateTimeFormatter inputFormatter;
    private DateTimeFormatter outputFormatter;
    private String dateFormat;

    /**
     * Constructs a TaskHandler with the specified formatters and date format.
     *
     * @param inputFormatter the formatter for parsing input dates
     * @param outputFormatter the formatter for formatting output dates
     * @param dateFormat the date format string
     */
    public TaskHandler(DateTimeFormatter inputFormatter, DateTimeFormatter outputFormatter, String dateFormat) {
        this.inputFormatter = inputFormatter;
        this.outputFormatter = outputFormatter;
        this.dateFormat = dateFormat;
    }

    /**
     * Handles the creation of a ToDo task.
     *
     * @param input the user input
     * @return the created ToDo task
     * @throws MissingArgumentException if the task description is missing
     */
    public ToDo handleToDos(String input) throws MissingArgumentException {
        String[] words = input.split("\\s+");
        if (words.length < 2 || words[1].trim().isEmpty()) {
            throw new MissingArgumentException();
        }
        String taskName = String.join(" ", Arrays.copyOfRange(words, 1, words.length));
        return new ToDo(taskName, outputFormatter);
    }

    /**
     * Handles the creation of a Deadline task.
     *
     * @param input the user input
     * @return the created Deadline task
     * @throws MissingArgumentException if the task description or deadline is missing
     */
    public Deadline handleDeadlines(String input) throws MissingArgumentException {
        String[] parts = input.split("/by ");
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new MissingArgumentException("   ____________________________________________________________\n  "
                + " You either got no /by or name for your deadline.. I don't know when your thing ends, a sorcerer "
                + "should always chant when they cast their spell.\n"
                + "   ____________________________________________________________");
        }
        try {
            LocalDateTime deadlineBy = LocalDateTime.parse(parts[1].trim(), inputFormatter);
            String description = parts[0].replace("deadline ", "").trim();
            return new Deadline(description, outputFormatter, deadlineBy);
        } catch (DateTimeParseException e) {
            throw new MissingArgumentException("   ____________________________________________________________\n   "
                + "Your formatting for the date of deadline is wrong or your date is invalid. "
                + "It should be "
                + dateFormat + ". Try again..\n"
                + "   ____________________________________________________________");
        }
    }

    /**
     * Handles the creation of an Event task.
     *
     * @param input the user input
     * @return the created Event task
     * @throws MissingArgumentException if the task description, start time, or end time is missing
     * @throws InvalidDateException if the start time is not before the end time
     */
    public Event handleEvents(String input) throws MissingArgumentException, InvalidDateException {
        String[] parts = input.split("/from ");
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new MissingArgumentException("   ____________________________________________________________\n  "
                + " Where's your /from (start date)? Can't have an event without the start time, just like how Purple"
                + " needs Blue.\n"
                + "   ____________________________________________________________");
        }
        String[] fromAndTo = parts[1].split("/to ");
        if (fromAndTo.length < 2 || fromAndTo[1].trim().isEmpty()) {
            throw new MissingArgumentException("   ____________________________________________________________\n  "
                + " Where's your /to (end date)? Can't have an event without the ending time, just like how Purple "
                + "needs red.\n"
                + "   ____________________________________________________________");
        }
        try {
            LocalDateTime from = LocalDateTime.parse(fromAndTo[0].trim(), inputFormatter);
            LocalDateTime to = LocalDateTime.parse(fromAndTo[1].trim(), inputFormatter);
            String description = parts[0].replace("event ", "").trim();
            if (!from.isBefore(to)) {
                throw new InvalidDateException("   ____________________________________________________________\n   "
                    + "The start date/time must be before the end date/time. Please provide valid timings.\n"
                    + "   ____________________________________________________________");
            }
            return new Event(description, outputFormatter, from, to);
        } catch (DateTimeParseException e) {
            throw new MissingArgumentException("   ____________________________________________________________\n   "
                + "Your formatting and/or the timings of the event is wrong. It should be " + dateFormat + ". Try "
                + "again..\n"
                + "   ____________________________________________________________");
        }
    }
}

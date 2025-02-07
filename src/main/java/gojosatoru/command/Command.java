package gojosatoru.command;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

import gojosatoru.exceptions.GojoException;
import gojosatoru.exceptions.InvalidCommandException;
import gojosatoru.exceptions.InvalidDateException;
import gojosatoru.exceptions.MissingArgumentException;
import gojosatoru.exceptions.TaskNotFoundException;
import gojosatoru.storage.Storage;
import gojosatoru.tasks.Deadline;
import gojosatoru.tasks.Event;
import gojosatoru.tasks.Task;
import gojosatoru.tasks.TaskList;
import gojosatoru.tasks.ToDo;
import gojosatoru.ui.Ui;

/**
 * The `Command` class is responsible for handling various user commands.
 * It processes input commands, interacts with the `TaskList`, and updates the `Storage`.
 * The class also formats dates and manages user interactions through the `Ui` component.
 */
public class Command {
    private DateTimeFormatter inputFormatter;
    private DateTimeFormatter outputFormatter;
    private String dateFormat;
    private Storage storage;
    private final Ui uiObject;

    /**
     * Constructs a `Command` object with the specified date formatters, date format, and UI component.
     *
     * @param inputFormatter the formatter for input dates
     * @param outputFormatter the formatter for output dates
     * @param dateFormat the date format string
     * @param uiObject the UI component for user interactions
     */
    public Command(DateTimeFormatter inputFormatter, DateTimeFormatter outputFormatter,
                   String dateFormat, Ui uiObject) {
        this.inputFormatter = inputFormatter;
        this.outputFormatter = outputFormatter;
        this.dateFormat = dateFormat;
        this.uiObject = uiObject;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    private int getIndex(String input) {
        String[] words = input.split("\\s+");
        return Integer.parseInt(words[1]) - 1;
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

    /**
     * Handles marking a task as completed.
     * Throws TaskNotFoundException if the task does not exist.
     *
     * @param userInput the user input
     * @param taskList the list of tasks
     * @throws TaskNotFoundException if the task does not exist
     */
    public void handleMark(String userInput, TaskList taskList) throws TaskNotFoundException {
        int index = getIndex(userInput);
        if (index < 0 || index >= taskList.size()) {
            throw new TaskNotFoundException();
        }
        Task pickedTask = taskList.getTask(index);
        try {
            pickedTask.markTask();
            storage.markTask(index, taskList);
            uiObject.showTaskMarked(pickedTask.showTask());
        } catch (IOException e) {
            uiObject.showStorageError();
        }
    }

    /**
     * Handles finding tasks by keyword.
     * Displays an error if the keyword is empty or no matching tasks are found.
     *
     * @param userInput the user input
     * @param taskList the list of tasks
     */
    public void handleFind(String userInput, TaskList taskList) {
        String keyword = userInput.substring(5).trim();
        if (keyword.isEmpty()) {
            uiObject.showError("The keyword for the find command cannot be empty.");
            return;
        }
        List<Task> matchingTasks = taskList.findTasks(keyword);
        if (matchingTasks.isEmpty()) {
            uiObject.showError("No matching tasks found.");
        } else {
            uiObject.showMatchingTasks(matchingTasks);
        }
    }

    /**
     * Handles listing all tasks.
     * Displays the task list header and each task in the list.
     *
     * @param userInput the user input
     * @param taskList the list of tasks
     */
    public void handleList(String userInput, TaskList taskList) {
        uiObject.showTaskListHeader();
        for (int i = 0; i < taskList.size(); i++) {
            uiObject.showTaskInList(i, taskList.getTask(i).showTask());
        }
        uiObject.showLine();
    }

    /**
     * Handles unmarking a task as not completed.
     * Throws TaskNotFoundException if the task does not exist.
     *
     * @param userInput the user input
     * @param taskList the list of tasks
     * @throws TaskNotFoundException if the task does not exist
     */
    public void handleUnmark(String userInput, TaskList taskList) throws TaskNotFoundException {
        int index = getIndex(userInput);
        if (index < 0 || index >= taskList.size()) {
            throw new TaskNotFoundException();
        }
        Task pickedTask = taskList.getTask(index);
        try {
            pickedTask.unmarkTask();
            storage.unmarkTask(index, taskList);
            uiObject.showTaskUnmarked(pickedTask.showTask());
        } catch (IOException e) {
            uiObject.showStorageError();
        }
    }

    /**
     * Handles the bye command.
     * Displays the goodbye message.
     *
     * @param userInput the user input
     * @param taskList the list of tasks
     */
    public void handleBye(String userInput, TaskList taskList) {
        uiObject.showBye();
    }

    /**
     * Handles deleting a task.
     * Throws TaskNotFoundException if the task does not exist.
     *
     * @param userInput the user input
     * @param taskList the list of tasks
     * @throws TaskNotFoundException if the task does not exist
     */
    public void handleDelete(String userInput, TaskList taskList) throws TaskNotFoundException {
        int index = getIndex(userInput);
        if (index < 0 || index >= taskList.size()) {
            throw new TaskNotFoundException();
        }
        Task pickedTask = taskList.getTask(index);
        try {
            uiObject.showTaskDeleted(pickedTask.showTask());
            storage.deleteTask(index, taskList);
        } catch (IOException e) {
            uiObject.showStorageError();
        }
    }

    /**
     * Handles adding a task.
     * Throws GojoException if the task type is invalid or arguments are missing.
     *
     * @param userInput the user input
     * @param taskList the list of tasks
     * @throws GojoException if the task type is invalid or arguments are missing
     */
    public void handleAddTask(String userInput, TaskList taskList) throws GojoException {
        String[] words = userInput.split("\\s+");
        String typeOfTask = words[0];
        if (words.length < 2 || words[1].trim().isEmpty()) {
            throw new MissingArgumentException();
        }
        Task newTask;
        switch (typeOfTask) {
        case "todo":
            newTask = handleToDos(userInput);
            break;
        case "deadline":
            newTask = handleDeadlines(userInput);
            break;
        case "event":
            newTask = handleEvents(userInput);
            break;
        default:
            throw new InvalidCommandException();
        }

        taskList.addTask(newTask);
        try {
            storage.addTask(newTask);
        } catch (IOException e) {
            uiObject.showStorageError();
        }
        uiObject.showTaskAdded(newTask.showTask(), taskList.size());
    }
}

package gojosatoru.parser;

import java.util.HashMap;
import java.util.Map;

import gojosatoru.command.Command;
import gojosatoru.command.CommandHandler;
import gojosatoru.exceptions.GojoException;
import gojosatoru.exceptions.InvalidCommandException;
import gojosatoru.tasks.TaskList;

/**
 * Parses user input and executes the corresponding commands.
 */
public class Parser {
    @SuppressWarnings("checkstyle:AbbreviationAsWordInName")
    private final Command command;
    private Map<String, CommandHandler> commandHandlers;

    /**
     * Parses user input and executes the corresponding commands.
     */
    public Parser(Command command) {
        this.command = command;
        initializeCommandHandlers();
    }

    private void initializeCommandHandlers() {
        commandHandlers = new HashMap<>();
        commandHandlers.put("bye", command::handleBye);
        commandHandlers.put("list", command::handleList);
        commandHandlers.put("find", command::handleFind);
        commandHandlers.put("mark", command::handleMark);
        commandHandlers.put("unmark", command::handleUnmark);
        commandHandlers.put("delete", command::handleDelete);
        commandHandlers.put("todo", command::handleAddTask);
        commandHandlers.put("deadline", command::handleAddTask);
        commandHandlers.put("event", command::handleAddTask);
    }

    /**
     * Parses the user input and executes the corresponding command.
     *
     * @param userInput the input string entered by the user
     * @param taskList the list of tasks to operate on
     * @throws GojoException if the command is invalid or an error occurs during execution
     */
    public String parseCommand(String userInput, TaskList taskList) throws GojoException {
        String commandKey = userInput.split("\\s+")[0];
        CommandHandler handler = commandHandlers.get(commandKey);
        if (handler != null) {
            return handler.handle(userInput, taskList);
        } else {
            throw new InvalidCommandException();
        }
    }
}

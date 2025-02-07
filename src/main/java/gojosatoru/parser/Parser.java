package gojosatoru.parser;

import gojosatoru.command.CommandHandler;
import gojosatoru.exceptions.*;

import gojosatoru.tasks.TaskList;

import gojosatoru.command.Command;

import java.util.*;

public class Parser {
  private final Command COMMAND;
  private Map<String, CommandHandler> commandHandlers;

  public Parser(Command COMMAND) {
    this.COMMAND = COMMAND;
    initializeCommandHandlers();
  }

  private void initializeCommandHandlers() {
    commandHandlers = new HashMap<>();
    commandHandlers.put("bye", COMMAND::handleBye);
    commandHandlers.put("list", COMMAND::handleList);
    commandHandlers.put("find", COMMAND::handleFind);
    commandHandlers.put("mark", COMMAND::handleMark);
    commandHandlers.put("unmark", COMMAND::handleUnmark);
    commandHandlers.put("delete", COMMAND::handleDelete);
    commandHandlers.put("todo", COMMAND::handleAddTask);
    commandHandlers.put("deadline", COMMAND::handleAddTask);
    commandHandlers.put("event", COMMAND::handleAddTask);
  }


  public void parseCommand(String userInput, TaskList taskList) throws GojoException {
    String commandKey = userInput.split("\\s+")[0];
    CommandHandler handler = commandHandlers.get(commandKey);
    System.out.println("parse is called");
    if (handler != null) {
      handler.handle(userInput, taskList);
    } else {
      throw new InvalidCommandException();
    }
  }
}

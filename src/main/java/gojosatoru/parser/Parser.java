package gojosatoru.parser;

import gojosatoru.exceptions.*;
import gojosatoru.tasks.Task;
import gojosatoru.tasks.TaskList;
import gojosatoru.ui.Ui;
import gojosatoru.handlers.TaskHandler;
import gojosatoru.storage.Storage;

import java.io.IOException;
import java.util.Objects;

/**
 * Parses user input and executes the corresponding commands.
 */
public class Parser {
  private TaskHandler taskHandler;
  private Storage storage;
  private Ui ui;

  /**
   * Constructs a Parser object with the specified task handler, storage, and user interface.
   *
   * @param taskHandler the handler for task operations
   * @param storage the storage for tasks
   * @param ui the user interface
   */
  public Parser(TaskHandler taskHandler, Storage storage, Ui ui) {
    this.taskHandler = taskHandler;
    this.storage = storage;
    this.ui = ui;
  }

  /**
   * Parses the user input and executes the corresponding command.
   *
   * @param userInput the user input
   * @param taskList the list of tasks
   * @throws GojoException if an error occurs while parsing or executing the command
   */
  public void parseCommand(String userInput, TaskList taskList) throws GojoException {
    if (Objects.equals(userInput, "bye")) {
      ui.showBye();
    } else if (Objects.equals(userInput, "list")) {
      ui.showTaskListHeader();
      for (int i = 0; i < taskList.size(); i++) {
        ui.showTaskInList(i, taskList.getTask(i).showTask());
      }
      ui.showLine();
    } else if (isMarkOrUnmark(userInput)) {
      handleMarkOrUnmarkDelete(userInput, taskList);
    } else {
      handleAddTask(userInput, taskList);
    }
  }

  /**
   * Checks if the input is a mark, unmark, or delete command.
   *
   * @param input the user input
   * @return true if the input is a mark, unmark, or delete command, false otherwise
   */
  private boolean isMarkOrUnmark(String input) {
    String[] words = input.split("\\s+");
    String firstWord = words[0];
    return firstWord.matches("mark") || firstWord.matches("unmark") || firstWord.matches("delete");
  }

  /**
   * Handles mark, unmark, and delete commands.
   *
   * @param userInput the user input
   * @param taskList the list of tasks
   * @throws TaskNotFoundException if the task to mark, unmark, or delete is not found
   */
  private void handleMarkOrUnmarkDelete(String userInput, TaskList taskList) throws TaskNotFoundException {
    if (isSecondWordNumberAndInList(userInput, taskList.size())) {
      int index = getIndex(userInput);
      if (index < 0 || index >= taskList.size()) {
        throw new TaskNotFoundException();
      }
      Task pickedTask = taskList.getTask(index);
      try {
        if (isMark(userInput)) {
          pickedTask.markTask();
          storage.markTask(index, taskList);
          ui.showTaskMarked(pickedTask.showTask());
        } else if (isUnmark(userInput)) {
          pickedTask.unmarkTask();
          storage.unmarkTask(index, taskList);
          ui.showTaskUnmarked(pickedTask.showTask());
        } else {
          ui.showTaskDeleted(pickedTask.showTask());
          storage.deleteTask(index, taskList);
        }
      } catch (IOException e) {
        ui.showStorageError();
      }
    } else {
      throw new TaskNotFoundException();
    }
  }

  /**
   * Checks if the second word in the input is a number and within the list size.
   *
   * @param input the user input
   * @param listSize the size of the task list
   * @return true if the second word is a number and within the list size, false otherwise
   */
  private boolean isSecondWordNumberAndInList(String input, int listSize) {
    String[] words = input.split("\\s+");
    if (words.length < 2) {
      return false;
    }
    String secondWord = words[1];
    try {
      int taskNumber = Integer.parseInt(secondWord);
      return taskNumber != 0 && taskNumber <= listSize;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  /**
   * Checks if the input is a mark command.
   *
   * @param input the user input
   * @return true if the input is a mark command, false otherwise
   */
  private boolean isMark(String input) {
    String[] words = input.split("\\s+");
    String firstWord = words[0];
    return firstWord.matches("mark");
  }

  /**
   * Checks if the input is an unmark command.
   *
   * @param input the user input
   * @return true if the input is an unmark command, false otherwise
   */
  private boolean isUnmark(String input) {
    String[] words = input.split("\\s+");
    String firstWord = words[0];
    return firstWord.matches("unmark");
  }

  /**
   * Gets the index of the task from the input.
   *
   * @param input the user input
   * @return the index of the task
   */
  private int getIndex(String input) {
    String[] words = input.split("\\s+");
    return Integer.parseInt(words[1]) - 1;
  }

  /**
   * Handles adding a new task.
   *
   * @param userInput the user input
   * @param items the list of tasks
   * @throws GojoException if an error occurs while adding the task
   */
  private void handleAddTask(String userInput, TaskList items) throws GojoException {
    String[] words = userInput.split("\\s+");
    String typeOfTask = words[0];
    if (words.length < 2 || words[1].trim().isEmpty()) {
      throw new MissingArgumentException();
    }
    Task newTask;
    switch (typeOfTask) {
      case "todo":
        newTask = taskHandler.handleToDos(userInput);
        break;
      case "deadline":
        newTask = taskHandler.handleDeadlines(userInput);
        break;
      case "event":
        newTask = taskHandler.handleEvents(userInput);
        break;
      default:
        throw new InvalidCommandException();
    }

    items.addTask(newTask);
    try {
      storage.addTask(newTask);
    } catch (IOException e) {
      ui.showStorageError();
    }
    ui.showTaskAdded(newTask.showTask(), items.size());
  }
}
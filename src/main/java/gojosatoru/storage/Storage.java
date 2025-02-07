package gojosatoru.storage;

import gojosatoru.handlers.TaskHandler;
import gojosatoru.tasks.*;

    import java.io.*;
    import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Handles the loading and saving of tasks to a file.
 */
public class Storage {
  private final String FILEPATH;
  private TaskHandler taskHandler;
  private DateTimeFormatter inputFormatter;
  private DateTimeFormatter outputFormatter;

  /**
   * Constructs a Storage object with the specified file path, task handler, input formatter, and output formatter.
   *
   * @param FILEPATH the path of the file to store tasks
   * @param taskHandler the handler for task operations
   * @param inputFormatter the formatter for parsing input dates
   * @param outputFormatter the formatter for formatting output dates
   */
  public Storage(String FILEPATH, TaskHandler taskHandler, DateTimeFormatter inputFormatter, DateTimeFormatter outputFormatter) {
    this.taskHandler = taskHandler;
    this.FILEPATH = FILEPATH;
    this.inputFormatter = inputFormatter;
    this.outputFormatter = outputFormatter;
  }

  /**
   * Loads tasks from the file.
   *
   * @return a list of tasks loaded from the file
   * @throws IOException if an I/O error occurs
   */
  public TaskList load() throws IOException {
    TaskList taskList = new TaskList();
    File file = new File(FILEPATH);
    if (!file.exists()) {
      file.getParentFile().mkdirs();
      file.createNewFile();
    } else {
      BufferedReader reader = new BufferedReader(new FileReader(file));
      String line;
      while ((line = reader.readLine()) != null) {
        String[] taskDetails = line.split(" \\| ");
        Task task;
        try {
          switch (taskDetails[0]) {
            case "T":
              task = taskHandler.handleToDos("todo " + taskDetails[2]);
              break;
            case "D":
              String by = LocalDateTime.parse(taskDetails[3], outputFormatter).format(inputFormatter);
              task = taskHandler.handleDeadlines("deadline " + taskDetails[2] + " /by " + by);
              break;
            case "E":
              String from = LocalDateTime.parse(taskDetails[3], outputFormatter).format(inputFormatter);
              String to = LocalDateTime.parse(taskDetails[4], outputFormatter).format(inputFormatter);
              task = taskHandler.handleEvents("event " + taskDetails[2] + " /from " + from + " /to " + to);
              break;
            default:
              throw new IOException("Corrupted data file.");
          }
          if (taskDetails[1].equals("1")) {
            task.markTask();
          }
          taskList.addTask(task);
        } catch (Exception e) {
          System.out.println("   ____________________________________________________________\n  " +
              "There was an error loading the task: " + line + "\n" +
              "   ____________________________________________________________\n");
        }
      }
      reader.close();
    }
    return taskList;
  }

  /**
   * Saves tasks to the file.
   *
   * @param taskList the list of tasks to save
   * @throws IOException if an I/O error occurs
   */
  public void save(TaskList taskList) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(FILEPATH));
    for (Task task : taskList.getTasks()) {
      writer.write(task.toSaveFormat() + "\n");
    }
    writer.close();
  }

  /**
   * Adds a task to the file.
   *
   * @param task the task to add
   * @throws IOException if an I/O error occurs
   */
  public void addTask(Task task) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(FILEPATH, true));
    writer.write(task.toSaveFormat() + "\n");
    writer.close();
  }

  /**
   * Deletes a task from the file by index.
   *
   * @param index the index of the task to delete
   * @param taskList the list of tasks
   * @throws IOException if an I/O error occurs
   */
  public void deleteTask(int index, TaskList taskList) throws IOException {
    taskList.deleteTask(index);
    save(taskList);
  }

  /**
   * Marks a task as completed in the file by index.
   *
   * @param index the index of the task to mark
   * @param taskList the list of tasks
   * @throws IOException if an I/O error occurs
   */
  public void markTask(int index, TaskList taskList) throws IOException {
    taskList.getTask(index).markTask();
    save(taskList);
  }

  /**
   * Unmarks a task as not completed in the file by index.
   *
   * @param index the index of the task to unmark
   * @param taskList the list of tasks
   * @throws IOException if an I/O error occurs
   */
  public void unmarkTask(int index, TaskList taskList) throws IOException {
    taskList.getTask(index).unmarkTask();
    save(taskList);
  }
}
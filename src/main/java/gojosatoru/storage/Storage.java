package gojosatoru.storage;

import gojosatoru.command.Command;
import gojosatoru.tasks.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Storage {
  private final String FILEPATH;
  private Command command;
  private DateTimeFormatter inputFormatter;
  private DateTimeFormatter outputFormatter;
  public Storage(String FILEPATH, DateTimeFormatter inputFormatter, DateTimeFormatter outputFormatter) {
    this.FILEPATH = FILEPATH;
    this.inputFormatter = inputFormatter;
    this.outputFormatter = outputFormatter;
  }

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
              task = command.handleToDos( "todo " + taskDetails[2]);
              break;
            case "D":
              String by = LocalDateTime.parse(taskDetails[3], outputFormatter).format(inputFormatter);
              task = command.handleDeadlines("deadline " + taskDetails[2] + " /by " + by);
              break;
            case "E":
              String from = LocalDateTime.parse(taskDetails[3], outputFormatter).format(inputFormatter);
              String to = LocalDateTime.parse(taskDetails[4], outputFormatter).format(inputFormatter);
              task = command.handleEvents("event " + taskDetails[2] + " /from " + from + " /to " + to);
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

  public void save(TaskList taskList) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(FILEPATH));
    for (Task task : taskList.getTasks()) {
      writer.write(task.toSaveFormat() + "\n");
    }
    writer.close();
  }

  public void addTask(Task task) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(FILEPATH, true));
    writer.write(task.toSaveFormat() + "\n");
    writer.close();
  }

  public void deleteTask(int index, TaskList taskList) throws IOException {
    taskList.deleteTask(index);
    save(taskList);
  }

  public void markTask(int index, TaskList taskList) throws IOException {
    taskList.getTask(index).markTask();
    save(taskList);
  }

  public void unmarkTask(int index, TaskList taskList) throws IOException {
    taskList.getTask(index).unmarkTask();
    save(taskList);
  }

}

package gojosatoru.storage;

import gojosatoru.handlers.TaskHandler;
import gojosatoru.tasks.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Storage {
  private final String FILEPATH;
  private TaskHandler taskHandler;
  private DateTimeFormatter inputFormatter;
  private DateTimeFormatter outputFormatter;
  public Storage(String FILEPATH, TaskHandler taskHandler, DateTimeFormatter inputFormatter, DateTimeFormatter outputFormatter) {
    this.taskHandler = taskHandler;
    this.FILEPATH = FILEPATH;
    this.inputFormatter = inputFormatter;
    this.outputFormatter = outputFormatter;
  }

  public ArrayList<Task> load() throws IOException {
    ArrayList<Task> tasks = new ArrayList<>();
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
              task = taskHandler.handleToDos( "todo " + taskDetails[2]);
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
          tasks.add(task);
        } catch (Exception e) {
          System.out.println("   ____________________________________________________________\n  " +
              "There was an error loading the task: " + line + "\n" +
              "   ____________________________________________________________\n");
        }
      }
      reader.close();
    }
      return tasks;
  }

  public void save(ArrayList<Task> tasks) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(FILEPATH));
    for (Task task : tasks) {
      writer.write(task.toSaveFormat() + "\n");
    }
    writer.close();
  }

  public void addTask(Task task) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(FILEPATH, true));
    writer.write(task.toSaveFormat() + "\n");
    writer.close();
  }

  public void deleteTask(int index, ArrayList<Task> tasks) throws IOException {
    tasks.remove(index);
    save(tasks);
  }

  public void markTask(int index, ArrayList<Task> tasks) throws IOException {
    tasks.get(index).markTask();
    save(tasks);
  }

  public void unmarkTask(int index, ArrayList<Task> tasks) throws IOException {
    tasks.get(index).unmarkTask();
    save(tasks);
  }

}

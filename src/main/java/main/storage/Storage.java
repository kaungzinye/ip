package main.storage;

import main.tasks.*;

import java.io.*;
import java.util.ArrayList;

public class Storage {
  private String filePath;

  public Storage(String filePath) {
    this.filePath = filePath;
  }

  public ArrayList<Task> load() throws IOException {
    ArrayList<Task> tasks = new ArrayList<>();
    File file = new File(filePath);
    if (!file.exists()) {
      file.getParentFile().mkdirs();
      file.createNewFile();
    } else {
      BufferedReader reader = new BufferedReader(new FileReader(file));
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(" \\| ");
        Task task;
        switch (parts[0]) {
          case "T":
            task = new ToDo(parts[2]);
            break;
          case "D":
            task = new Deadline(parts[2], parts[3]);
            break;
          case "E":
            task = new Event(parts[2], parts[3], parts[4]);
            break;
          default:
            throw new IOException("Corrupted data file.");
        }
        if (parts[1].equals("1")) {
          task.markTask();
        }
        tasks.add(task);
      }
      reader.close();
    }
    return tasks;
  }

  public void save(ArrayList<Task> tasks) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
    for (Task task : tasks) {
      writer.write(task.toSaveFormat() + "\n");
    }
    writer.close();
  }

  public void addTask(Task task) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
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

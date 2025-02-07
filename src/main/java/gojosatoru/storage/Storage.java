package gojosatoru.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import gojosatoru.handlers.TaskHandler;
import gojosatoru.tasks.Task;
import gojosatoru.tasks.TaskList;

public class Storage {
    private final String filePath;
    private TaskHandler taskHandler;
    private DateTimeFormatter inputFormatter;
    private DateTimeFormatter outputFormatter;

    public Storage(String filePath, TaskHandler taskHandler, DateTimeFormatter inputFormatter,
                   DateTimeFormatter outputFormatter) {
        this.taskHandler = taskHandler;
        this.filePath = filePath;
        this.inputFormatter = inputFormatter;
        this.outputFormatter = outputFormatter;
    }

    public TaskList load() throws IOException {
        TaskList taskList = new TaskList();
        File file = new File(filePath);
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
                    System.out.println("   ____________________________________________________________\n  "
                        + "There was an error loading the task: " + line + "\n"
                        + "   ____________________________________________________________\n");
                }
            }
            reader.close();
        }
        return taskList;
    }

    public void save(TaskList taskList) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        for (Task task : taskList.getTasks()) {
            writer.write(task.toSaveFormat() + "\n");
        }
        writer.close();
    }

    public void addTask(Task task) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
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

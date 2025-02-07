package gojosatoru.parser;

import java.io.IOException;
import java.util.Objects;

import gojosatoru.exceptions.GojoException;
import gojosatoru.exceptions.InvalidCommandException;
import gojosatoru.exceptions.MissingArgumentException;
import gojosatoru.exceptions.TaskNotFoundException;
import gojosatoru.handlers.TaskHandler;
import gojosatoru.storage.Storage;
import gojosatoru.tasks.Task;
import gojosatoru.tasks.TaskList;
import gojosatoru.ui.Ui;

public class Parser {
    private TaskHandler taskHandler;
    private Storage storage;
    private Ui ui;

    public Parser(TaskHandler taskHandler, Storage storage, Ui ui) {
        this.taskHandler = taskHandler;
        this.storage = storage;
        this.ui = ui;
    }

    public void parseCommand(String userInput, TaskList taskList) throws GojoException {
        if (Objects.equals(userInput, "bye")) {
            ui.showBye();
        } else if (Objects.equals(userInput, "list")) {
            ui.showTaskListHeader();
            for (int i = 0; i < taskList.size(); i++) {
                //System.out.println("    " + Integer.toString(i + 1) + ". " + taskList.get(i).showTask());
                ui.showTaskInList(i, taskList.getTask(i).showTask());
            }
            ui.showLine();
        } else if (isMarkOrUnmark(userInput)) {
            handleMarkOrUnmarkDelete(userInput, taskList);
        } else {
            handleAddTask(userInput, taskList);
        }
    }

    private boolean isMarkOrUnmark(String input) {
        String[] words = input.split("\\s+");
        String firstWord = words[0];
        return firstWord.matches("mark") || firstWord.matches("unmark") || firstWord.matches("delete");
    }

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

    private boolean isMark(String input) {
        String[] words = input.split("\\s+");
        String firstWord = words[0];
        return firstWord.matches("mark");
    }

    private boolean isUnmark(String input) {
        String[] words = input.split("\\s+");
        String firstWord = words[0];
        return firstWord.matches("unmark");
    }

    private int getIndex(String input) {
        String[] words = input.split("\\s+");
        return Integer.parseInt(words[1]) - 1;
    }

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

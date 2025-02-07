package gojosatoru.command;

import gojosatoru.exceptions.GojoException;
import gojosatoru.tasks.TaskList;

@FunctionalInterface
public interface CommandHandler {
    void handle(String userInput, TaskList taskList) throws GojoException;
}

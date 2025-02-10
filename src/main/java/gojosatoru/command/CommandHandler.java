package gojosatoru.command;

import gojosatoru.exceptions.GojoException;
import gojosatoru.tasks.TaskList;

/**
 * Maps each command keyword to a valid command handler.
 */
@FunctionalInterface
public interface CommandHandler {
    String handle(String userInput, TaskList taskList) throws GojoException;
}

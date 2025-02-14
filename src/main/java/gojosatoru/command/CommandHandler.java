package gojosatoru.command;

import gojosatoru.exceptions.GojoException;

/**
 * Maps each command keyword to a valid command handler.
 */
@FunctionalInterface
public interface CommandHandler {
    String handle(String userInput) throws GojoException;
}

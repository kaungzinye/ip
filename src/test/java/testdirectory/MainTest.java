// MainTest.java
package testdirectory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import gojosatoru.command.Command;
import gojosatoru.exceptions.GojoException;
import gojosatoru.exceptions.InvalidCommandException;
import gojosatoru.exceptions.MissingArgumentException;
import gojosatoru.parser.Parser;
import gojosatoru.storage.Storage;
import gojosatoru.tasks.TaskList;
import gojosatoru.ui.Ui;

public class MainTest {
    private static final String FILE_PATH = "./src/main/data/cursedEnergy.txt";
    private static final Ui UI = new Ui();
    private static final String inputDateFormat = "dd/MM/yyyy HHmm";
    private static final String outputDateFormat = "MMM dd yyyy HHmm";
    private static final DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(inputDateFormat);
    private static final DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(outputDateFormat);
    private Command command;
    private Storage storage;
    private Parser parser;
    private TaskList taskList;

    /**
     + Sets up the test environment before each test.
     + Initializes the command, storage, parser, and task list.
     + Clears the storage by saving an empty TaskList.
     */
    @BeforeEach
    void setUp() throws Exception {
        command = new Command(inputFormatter, outputFormatter, inputDateFormat, UI);
        storage = new Storage(FILE_PATH, inputFormatter, outputFormatter);
        command.setStorage(storage);
        parser = new Parser(command);
        taskList = new TaskList();
        storage.save(taskList); // Clear storage by saving an empty TaskList
    }

    /**
     + Tests adding a ToDo task.
     + Verifies that the task list size is 1 after adding the task.
     */
    @Test
    void testAddTask() throws GojoException {
        parser.parseCommand("todo read book", taskList);
        assertEquals(1, taskList.size());
    }

    /**
     + Tests adding a Deadline task.
     + Verifies that the task list size is 1 after adding the task.
     */
    @Test
    void testAddDeadline() throws GojoException {
        parser.parseCommand("deadline return book /by 12/12/2023 1800", taskList);
        assertEquals(1, taskList.size());
    }

    /**
     + Tests adding an Event task.
     + Verifies that the task list size is 1 after adding the task.
     */
    @Test
    void testAddEvent() throws GojoException {
        parser.parseCommand("event project meeting /from 12/12/2023 1400 /to 12/12/2023 1600", taskList);
        assertEquals(1, taskList.size());
    }

    /**
     + Tests listing all tasks.
     + Verifies that the task list size is 3 after adding three tasks.
     */
    @Test
    void testListTasks() throws GojoException {
        parser.parseCommand("todo read book", taskList);
        parser.parseCommand("deadline return book /by 12/12/2023 1800", taskList);
        parser.parseCommand("event project meeting /from 12/12/2023 1400 /to 12/12/2023 1600", taskList);
        assertEquals(3, taskList.size());
    }

    /**
     + Tests marking a task as completed.
     + Verifies that the task is marked as completed.
     */
    @Test
    void testMarkTask() throws GojoException {
        parser.parseCommand("todo read book", taskList);
        parser.parseCommand("mark 1", taskList);
        assertEquals(true, taskList.getTask(0).isCompleted());
    }

    /**
     + Tests unmarking a task as not completed.
     + Verifies that the task is marked as not completed.
     */
    @Test
    void testUnmarkTask() throws GojoException {
        parser.parseCommand("todo read book", taskList);
        parser.parseCommand("mark 1", taskList);
        parser.parseCommand("unmark 1", taskList);
        assertEquals(false, taskList.getTask(0).isCompleted());
    }

    /**
     + Tests deleting a task.
     + Verifies that the task list size is 0 after deleting the task.
     */
    @Test
    void testDeleteTask() throws GojoException {
        parser.parseCommand("todo read book", taskList);
        parser.parseCommand("delete 1", taskList);
        assertEquals(0, taskList.size());
    }

    /**
     + Tests marking a non-existent task.
     + Verifies that the correct exception message is thrown.
     */
    @Test
    void testMarkNonExistentTask() {
        GojoException exception = assertThrows(GojoException.class, () -> {
            parser.parseCommand("mark 1", taskList);
        });
        assertEquals("   ____________________________________________________________\n   " +
            "My Six Eyes I can't find your task, because you it doesn't exist you idiot.\n   Not surprised since I'm stronger than you..\n   Try again...\n" +
            "   ____________________________________________________________", exception.getMessage());
    }

    /**
     + Tests finding a task by keyword.
     + Verifies that the task list size is 1 after finding the task.
     */
    @Test
    void testFindTask() throws GojoException {
        parser.parseCommand("todo read book", taskList);
        parser.parseCommand("find read", taskList);
        assertEquals(1, taskList.size());
    }

    /**
     + Tests an invalid command.
     + Verifies that the correct exception message is thrown.
     */
    @Test
    void testInvalidCommand() {
        InvalidCommandException exception = assertThrows(InvalidCommandException.class, () -> {
            parser.parseCommand("invalid command", taskList);
        });
        assertEquals("   ____________________________________________________________\n  "
            + " Oi... I don't know what that means, didn't teach ya that in Jujutsu High..\n"
            + "   ____________________________________________________________", exception.getMessage());
    }

    /**
     + Tests a missing argument for a command.
     + Verifies that the correct exception message is thrown.
     */
    @Test
    void testMissingArgument() {
        MissingArgumentException exception = assertThrows(MissingArgumentException.class, () -> {
            parser.parseCommand("todo", taskList);
        });
        assertEquals("   ____________________________________________________________\n  "
            + " Even with my Six Eyes, I can't tell what the name of your task is... "
            + "BECAUSE IT'S EMPTY! WRITE IT "
            + "AGAIN IDIOT!\n"
            + "   ____________________________________________________________", exception.getMessage());
    }
}
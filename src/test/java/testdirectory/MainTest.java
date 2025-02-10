// MainTest.java
package testdirectory;

import gojosatoru.GojoSatoru;
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
        storage = new Storage(FILE_PATH, command, inputFormatter, outputFormatter);
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
            "My Six Eyes can't find your task, because it doesn't exist you idiot.\n   Not surprised since I'm stronger than you..\n   Try again...\n" +
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
    /**
     + Tests an invalid date format.
     + Verifies that the correct exception message is thrown.
     */
    @Test
    public void testInvalidCommandShowsError() throws GojoException{
        String response = GojoSatoru.getResponse("invalid command");
        assertEquals("Oi... I don't know what that means, didn't teach ya that in Jujutsu High..", response);
    }
    /**
     + Tests an invalid date format.
     + Verifies that the correct exception message is thrown.
     */
    @Test
    public void testEmptyTaskShowsError() throws GojoException {
        String response = GojoSatoru.getResponse("todo ");
        assertEquals("Even with my Six Eyes, I can't tell what the name of your task is... BECAUSE IT'S EMPTY! WRITE IT AGAIN IDIOT!", response);
    }
    /**
     + Tests an invalid date format.
     + Verifies that the correct exception message is thrown.
     */
    @Test
    public void testInvalidDateShowsError() throws GojoException {
        String response = GojoSatoru.getResponse("deadline submit report /by 32/13/2020 2500");
        assertEquals("Your formatting for the date of deadline is wrong or your date is invalid. It should be "
            + inputDateFormat + ". Try again..", response);
    }
    /**
     + Tests an invalid date format.
     + Verifies that the correct exception message is thrown.
     */
    @Test
    public void testTaskNotFoundShowsError() throws GojoException {
        String response = GojoSatoru.getResponse("delete 999");
        assertEquals("My Six Eyes can't find your task, because it doesn't exist you idiot. Not surprised since" +
            " I'm stronger than you.. Try again...", response);
    }

    @Test
    public void testSpecialCharacterTask() throws GojoException {
        String response = GojoSatoru.getResponse("todo !@#$%^&*()");
        assertEquals("Got it. I've added this task:\n      [T][ ] !@#$%^&*()\n   Now you have 1 tasks in the list.", response);
    }

    @Test
    public void testMissingDeadlineDateShowsError() throws GojoException {
        String response = GojoSatoru.getResponse("deadline return book /by");
        assertEquals("The date provided is invalid or incorrectly formatted. Please check and try again.", response);
    }

    @Test
    public void testMissingEventStartTimeShowsError() throws GojoException {
        String response = GojoSatoru.getResponse("event project meeting /from /to 12/12/2023 1600");
        assertEquals("The event must have a start and end time. Please check the format: /from <start time> /to <end time>", response);
    }

    @Test
    public void testMissingEventEndTimeShowsError() throws GojoException {
        String response = GojoSatoru.getResponse("event project meeting /from 12/12/2023 1400 /to");
        assertEquals("The event must have a start and end time. Please check the format: /from <start time> /to <end time>", response);
    }

    @Test
    public void testFindNonExistentTask() throws GojoException {
        parser.parseCommand("todo read book", taskList);
        String response = GojoSatoru.getResponse("find banana");
        assertEquals("No matching tasks found.", response);
    }

    @Test
    public void testMarkNegativeIndexShowsError() throws GojoException {
        String response = GojoSatoru.getResponse("mark -1");
        assertEquals("My Six Eyes can't find your task, because it doesn't exist you idiot. Not surprised since I'm"
            + " stronger than you.. Try again...", response);
    }

    @Test
    public void testMarkOutOfRangeTaskShowsError() throws GojoException {
        String response = GojoSatoru.getResponse("mark 100");
        assertEquals("My Six Eyes can't find your task, because it doesn't exist you idiot. Not surprised since I'm"
            + " stronger than you.. Try again...", response);
    }

    @Test
    public void testDeleteNegativeIndexShowsError() throws GojoException {
        String response = GojoSatoru.getResponse("delete -1");
        assertEquals("My Six Eyes can't find your task, because it doesn't exist you idiot. Not surprised since" +
            " I'm stronger than you.. Try again...", response);
    }

    @Test
    public void testDeleteOutOfRangeIndexShowsError() throws GojoException {
        String response = GojoSatoru.getResponse("delete 999");
        assertEquals("My Six Eyes can't find your task, because it doesn't exist you idiot. Not surprised since I'm stronger than you.. Try again...", response);
    }



}
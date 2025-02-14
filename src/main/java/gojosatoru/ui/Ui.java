package gojosatoru.ui;

import java.util.List;
import java.util.Scanner;

import gojosatoru.tasks.Task;
import gojosatoru.tasks.TaskList;

/**
 * Handles user interactions.
 */
public class Ui {
    private Scanner scanner;
    private String nextInput;

    /**
     * Constructs a Ui object.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads a command from the user.
     *
     * @return the command entered by the user
     */
    public String readCommand() {
        if (nextInput != null) {
            String input = nextInput;
            nextInput = null;
            return input;
        }
        return scanner.nextLine();
    }

    /**
     * Displays the welcome message.
     * @return the welcome message.
     */
    public String showWelcome() {
        System.out.println("   ____________________________________________________________");
        System.out.println("   Hello! I'm Gojo Satoru");
        System.out.println("   Am I the strongest chatbot because I'm Gojo Satoru");
        System.out.println("   or am I Gojo Satoru because I am the strongest chatbot?");
        System.out.println("   What can I do for you?");
        System.out.println("   ____________________________________________________________");
        return "Hello! I'm Gojo Satoru\n   Am I the strongest chatbot because I'm Gojo Satoru\n   "
            + "or am I Gojo Satoru because I am the strongest chatbot?\n   What can I do for you?\n   ";
    }

    /**
     * Displays a line separator.
     * @return a line separator.
     */
    public String showLine() {
        String line = "   ____________________________________________________________";
        System.out.println(line);
        return line;
    }

    /**
     * Displays the goodbye message.
     * @return the goodbye message.
     */
    public String showBye() {
        String goodbyeMessage = "You're weak... the next time I see you, I'd win.";
        showLine();
        System.out.println("   " + goodbyeMessage);
        showLine();
        return goodbyeMessage;
    }

    /**
     * Displays a message indicating a task has been marked as done.
     *
     * @param task the task that was marked as done
     * @return a message indicating a task has been marked as done
     */
    public String showTaskMarked(String task) {
        String markedTaskMessage = "Nice! I've marked this task as done:\n     " + task;
        System.out.println("   ____________________________________________________________\n   "
            + markedTaskMessage + "\n   "
            + "____________________________________________________________");
        return markedTaskMessage;
    }

    /**
     * Displays a message indicating a task has been marked as not done.
     *
     * @param task the task that was marked as not done
     * @return a message indicating a task has been marked as not done
     */
    public String showTaskUnmarked(String task) {
        String unmarkedTask = "OK, I've marked this task as not done yet:\n     " + task;
        System.out.println("   ____________________________________________________________\n   "
            + unmarkedTask + "\n   "
            + "____________________________________________________________");
        return unmarkedTask;
    }

    /**
     * Displays a message indicating a task has been deleted.
     *
     * @param task the task that was deleted
     * @return a message indicating a task has been deleted
     */
    public String showTaskDeleted(String task) {
        String deletedTaskMessage = "OK, I'm deleting this task:\n     " + task;
        System.out.println("   ____________________________________________________________\n   "
            + deletedTaskMessage + "\n   "
            + "____________________________________________________________");
        return deletedTaskMessage;
    }

    /**
     * Displays a message indicating a task has been added.
     *
     * @param task the task that was added
     * @param size the current number of tasks in the list
     * @return a message indicating a task has been added
     */
    public String showTaskAdded(String task, int size) {
        String addedTaskMessage = "Got it. I've added this task:\n      " + task + "\n   Now you have "
            + size + " tasks in the list.";
        showLine();
        System.out.println("   " + addedTaskMessage);
        showLine();
        return addedTaskMessage;
    }
    /**
     * Displays a storage error message.
     * @return the storage error message.
     */
    public String showStorageError() {
        String storageErrorMessage = "I'm sorry, I can't mark this task as done because something wrong happened "
            + "with my cursed energy(my "
            + "storage).\n";
        System.out.println("   ____________________________________________________________\n   "
            + storageErrorMessage
            + "   ____________________________________________________________\n");
        return storageErrorMessage;
    }

    /**
     * Displays the header for the task list.
     */
    public void showTaskListHeader() {
        showLine();
        System.out.println("   Here are the tasks in your list:");
    }

    /**
     * Displays an error message.
     *
     * @param message the error message to display
     * @return the error message
     */
    public String showError(String message, boolean printLines) {
        if (printLines) {
            System.out.println("   ____________________________________________________________");
            System.out.println("   " + message);
            System.out.println("   ____________________________________________________________");
        } else {
            System.out.println(message);
        }
        return message;
    }

    /**
     * Displays a task in the list.
     *
     * @param taskList the list of tasks
     * @return the list of tasks
     */
    public String showTasksInList(TaskList taskList) {
        showTaskListHeader();
        String tasksInListMessage = "";
        for (int i = 0; i < taskList.size(); i++) {
            tasksInListMessage = tasksInListMessage + (i + 1) + "." + taskList.getTask(i).showTask() + "\n";
        }
        System.out.println("    " + tasksInListMessage);
        showLine();
        return tasksInListMessage;
    }
    /**
     * Displays the list of tasks that match the search keyword.
     *
     * @param matchingTasks the list of tasks that match the search keyword
     * @return the list of tasks that match the search keyword
     */
    public String showMatchingTasks(List<Task> matchingTasks) {
        String listOfMatchingTasks = "";
        for (int i = 0; i < matchingTasks.size(); i++) {
            listOfMatchingTasks = listOfMatchingTasks + "   " + (i + 1) + "." + matchingTasks.get(i).showTask() + "\n";;
        }
        String matchingTasksMessage = "Here are the matching tasks in your list:\n" + listOfMatchingTasks;
        System.out.println("   ____________________________________________________________");
        System.out.println("   " + matchingTasksMessage);
        System.out.println("   ____________________________________________________________");
        return matchingTasksMessage;
    }
    /**
     * Displays a message indicating a task has been updated.
     *
     * @param nextInput the input string for the next command
     */
    public void setNextInput(String nextInput) {
        this.nextInput = nextInput;
    }
}

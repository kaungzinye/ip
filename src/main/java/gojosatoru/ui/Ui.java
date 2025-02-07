package gojosatoru.ui;

import java.util.Scanner;

/**
 * Handles user interactions.
 */
public class Ui {
  private Scanner scanner;

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
    return scanner.nextLine();
  }

  /**
   * Displays the welcome message.
   */
  public void showWelcome() {
    System.out.println("   ____________________________________________________________");
    System.out.println("   Hello! I'm Gojo Satoru");
    System.out.println("   Am I the strongest chatbot because I'm Gojo Satoru");
    System.out.println("   or am I Gojo Satoru because I am the strongest chatbot?");
    System.out.println("   What can I do for you?");
    System.out.println("   ____________________________________________________________");
  }

  /**
   * Displays a line separator.
   */
  public void showLine() {
    System.out.println("   ____________________________________________________________");
  }

  /**
   * Displays the goodbye message.
   */
  public void showBye() {
    showLine();
    System.out.println("   You're weak... the next time I see you, I'd win.");
    showLine();
  }

  /**
   * Displays a message indicating a task has been marked as done.
   *
   * @param task the task that was marked as done
   */
  public void showTaskMarked(String task) {
    System.out.println("   ____________________________________________________________\n   " +
        "Nice! I've marked this task as done:\n     " + task + "\n   ____________________________________________________________");
  }

  /**
   * Displays a message indicating a task has been marked as not done.
   *
   * @param task the task that was marked as not done
   */
  public void showTaskUnmarked(String task) {
    System.out.println("   ____________________________________________________________\n   " +
        "OK, I've marked this task as not done yet:\n     " + task + "\n   ____________________________________________________________");
  }

  /**
   * Displays a message indicating a task has been deleted.
   *
   * @param task the task that was deleted
   */
  public void showTaskDeleted(String task) {
    System.out.println("   ____________________________________________________________\n   " +
        "OK, I'm deleting this task:\n     " + task + "\n   ____________________________________________________________");
  }

  /**
   * Displays a message indicating a task has been added.
   *
   * @param task the task that was added
   * @param size the current number of tasks in the list
   */
  public void showTaskAdded(String task, int size) {
    showLine();
    System.out.println("   Got it. I've added this task:\n      " + task + "\n   Now you have " + size + " tasks in the list.");
    showLine();
  }

  /**
   * Displays a storage error message.
   */
  public void showStorageError() {
    System.out.println("   ____________________________________________________________\n   " +
        "I'm sorry, I can't mark this task as done because something wrong happened with my cursed energy(my storage).\n" +
        "   ____________________________________________________________\n");
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
   */
  public void showError(String message) {
    System.out.println(message);
  }

  /**
   * Displays a task in the list.
   *
   * @param index the index of the task
   * @param task the task to display
   */
  public void showTaskInList(int index, String task) {
    System.out.println("    " + (index + 1) + ". " + task);
  }
}
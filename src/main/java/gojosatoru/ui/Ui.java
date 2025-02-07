package gojosatoru.ui;

import java.util.Scanner;

public class Ui {
    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showWelcome() {
        System.out.println("   ____________________________________________________________");
        System.out.println("   Hello! I'm Gojo Satoru");
        System.out.println("   Am I the strongest chatbot because I'm Gojo Satoru");
        System.out.println("   or am I Gojo Satoru because I am the strongest chatbot?");
        System.out.println("   What can I do for you?");
        System.out.println("   ____________________________________________________________");
    }

    public void showLine() {
        System.out.println("   ____________________________________________________________");
    }

    public void showBye() {
        showLine();
        System.out.println("   You're weak... the next time I see you, I'd win.");
        showLine();
    }

    public void showTaskMarked(String task) {
        System.out.println("   ____________________________________________________________\n   "
            + "Nice! I've marked this task as done:\n     " + task + "\n   "
            + "____________________________________________________________");
    }

    public void showTaskUnmarked(String task) {
        System.out.println("   ____________________________________________________________\n   "
            + "OK, I've marked this task as not done yet:\n     " + task + "\n   "
            + "____________________________________________________________");
    }

    public void showTaskDeleted(String task) {
        System.out.println("   ____________________________________________________________\n   "
            + "OK, I'm deleting this task:\n     " + task + "\n   "
            + "____________________________________________________________");
    }

    public void showTaskAdded(String task, int size) {
        showLine();
        System.out.println("   Got it. I've added this task:\n      " + task + "\n   Now you have "
            + size + " tasks in the list.");
        showLine();
    }

    public void showStorageError() {
        System.out.println("   ____________________________________________________________\n   "
            + "I'm sorry, I can't mark this task as done because something wrong happened with my cursed energy(my "
            + "storage).\n"
            + "   ____________________________________________________________\n");
    }

    public void showTaskListHeader() {
        showLine();
        System.out.println("   Here are the tasks in your list:");
    }

    public void showError(String message) {
        System.out.println(message);
    }

    public void showTaskInList(int index, String task) {
        System.out.println("    " + (index + 1) + ". " + task);
    }
}

package gojosatoru;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Scanner;
import java.util.ArrayList;
import gojosatoru.exceptions.*;
import gojosatoru.handlers.TaskHandler;
import gojosatoru.storage.*;
import gojosatoru.tasks.*;


public class GojoSatoru {
    private static final String FILE_PATH = "./src/main/data/cursedEnergy.txt";
    private static Storage storage;

    private static String inputDateFormat = "dd/MM/yyyy HHmm";
    private static String outputDateFormat = "MMM dd yyyy HHmm";
    private static DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(inputDateFormat);
    private static DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(outputDateFormat);
    private static TaskHandler taskHandler = new TaskHandler(inputFormatter, outputFormatter, inputDateFormat);

    public GojoSatoru() {
        storage = new Storage(FILE_PATH, taskHandler, inputFormatter, outputFormatter);
    }

    public static boolean isSecondWordNumberAndInList(String input, int listSize) {
        String[] words = input.split("\\s+");
        if (words.length < 2){
            return false;
        }
        String secondWord = words[1];
        try {
            int taskNumber = Integer.parseInt(secondWord);
            return taskNumber != 0 && taskNumber <= listSize;
        } catch(NumberFormatException e) {
            return false;
        }
    }
    public static void handleMarkOrUnmarkDelete(String userInput, ArrayList<Task> items) throws TaskNotFoundException {
        if (isSecondWordNumberAndInList(userInput, items.size())) {
            int index = getIndex(userInput);
            if (index < 0 || index >= items.size()) {
                throw new TaskNotFoundException();
            }
            Task pickedTask = items.get(index);
            try {
                if (isMark(userInput)) {
                    pickedTask.markTask();
                    storage.markTask(index, items);
                    System.out.println("   ____________________________________________________________\n   " +
                        "Nice! I've marked this task as done:\n     " +
                        pickedTask.showTask() + "\n   ____________________________________________________________\n");
                } else if (isUnmark(userInput)) {
                    pickedTask.unmarkTask();
                    storage.unmarkTask(index, items);
                    System.out.println("   ____________________________________________________________\n   " +
                        "OK, I've marked this task as not done yet:\n     " +
                        pickedTask.showTask() + "\n   ____________________________________________________________\n");
                } else {
                    System.out.println("   ____________________________________________________________\n   " +
                        "OK, I'm deleting this task:\n     " +
                        pickedTask.showTask() + "\n   ____________________________________________________________\n");
                    storage.deleteTask(index, items);
                }
            } catch (IOException e) {
                System.out.println("   ____________________________________________________________\n   " +
                    "I'm sorry, I can't mark this task as done because something wrong happened with my cursed energy(my storage).\n" +
                    "   ____________________________________________________________\n");
            }
        } else {
            throw new TaskNotFoundException();
        }
    }

    public static boolean isMarkOrUnmark(String input) {
        String[] words = input.split("\\s+");
        String firstWord = words[0];
        return firstWord.matches("mark") || firstWord.matches("unmark" ) || firstWord.matches("delete");

    }
    public static boolean isMark(String input) {
        String[] words = input.split("\\s+");
        String firstWord = words[0];
        return firstWord.matches("mark");
    }

    public static boolean isUnmark(String input) {
        String[] words = input.split("\\s+");
        String firstWord = words[0];
        return firstWord.matches("unmark");
    }

    public static int getIndex(String input) {
        String[] words = input.split("\\s+");
        return Integer.parseInt(words[1])-1;
    }

    public static void main(String[] args) throws Exception {
        GojoSatoru gojo = new GojoSatoru();
        ArrayList<Task> items = storage.load();
        Scanner userScanner = new Scanner(System.in);
        String introText = "   ____________________________________________________________\n" +
            "   Hello! I'm Gojo Satoru\n" +
            "   Am I the strongest chatbot because I'm Gojo Satoru\n" +
            "   or am I Gojo Statoru because I am the strongest chatbot?\n" +
            "   What can I do for you?\n" + "   ____________________________________________________________\n";
        System.out.println(introText);
        while (true){
            String userInput = userScanner.nextLine();
            if (Objects.equals(userInput, "bye")) {
                System.out.println( "   ____________________________________________________________\n  " +
                    " You're weak... the next time I see you, I'd win.\n"
                    + "   ____________________________________________________________\n");
                break;
            }
            else if (Objects.equals(userInput, "list")){
                System.out.println("   ____________________________________________________________\n   " +
                    "Here are the tasks in your list:");
                for (int i = 0; i < items.size(); i++) {
                    System.out.println("    " + Integer.toString(i + 1) + ". " + items.get(i).showTask());
                }
                System.out.println("   ____________________________________________________________\n");
            }
            else if (isMarkOrUnmark(userInput)) {
                try {
                    handleMarkOrUnmarkDelete(userInput, items);
                } catch (TaskNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }
            else {
                try {
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

                    items.add(newTask);
                    storage.addTask(newTask);
                    System.out.println("   ____________________________________________________________\n   Got it. I've added this task:\n      " +
                        newTask.showTask() + "\n   Now you have " + items.size() + " tasks in the list.\n"
                        + "   ____________________________________________________________\n");
                } catch (GojoException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

    }
}

package main;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.ArrayList;
import main.exceptions.*;
import main.storage.*;
import main.tasks.*;


public class GojoSatoru {
    private static final String FILE_PATH = "../data/cursedEnergy.txt";
    private static Storage storage;

    public GojoSatoru() {
        storage = new Storage(FILE_PATH);
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
                        "Nice! I've marked this task as done: \n   " +
                        pickedTask.showTask() + "\n   ____________________________________________________________\n");
                } else if (isUnmark(userInput)) {
                    pickedTask.unmarkTask();
                    storage.unmarkTask(index, items);
                    System.out.println("   ____________________________________________________________\n   " +
                        "OK, I've marked this task as not done yet: \n   " +
                        pickedTask.showTask() + "\n   ____________________________________________________________\n");
                } else {
                    System.out.println("   ____________________________________________________________\n   " +
                        "OK, I'm deleting this task: \n   " +
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

    public ToDo handleToDos(String input) throws MissingArgumentException{
        String[] words = input.split("\\s+");
        if (words.length < 2 || words[1].trim().isEmpty()) {
            throw new MissingArgumentException();
        }
        String taskName = String.join(" ", Arrays.copyOfRange(words, 1, words.length));
        return new ToDo(taskName);
    }

    public Deadline handleDeadlines(String input) throws MissingArgumentException {
        String[] parts = input.split("/by ");
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new MissingArgumentException("   ____________________________________________________________\n  " +
                "You either got no /by or name for your deadline.. I don't know when your thing ends, a sorcerer should always chant when they cast their spell.\n" +
                "   ____________________________________________________________\n");
        }
        String description = parts[0].replace("deadline ", "").trim() + " (by: " + parts[1].trim() + ")";
        String by = parts[1].trim();
        return new Deadline(description, by);
    }


    public Event handleEvents(String input) throws MissingArgumentException {
        String[] parts = input.split("/from ");
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new MissingArgumentException("   ____________________________________________________________\n  " +
                "Where's your /from (start date)? Can't have an event without the start time, just like how Purple needs Blue.\n" +
                "   ____________________________________________________________\n");
        }
        String[] fromAndTo = parts[1].split("/to ");
        if (fromAndTo.length < 2 || fromAndTo[1].trim().isEmpty()) {
            throw new MissingArgumentException("   ____________________________________________________________\n  " +
                "Where's your /to (end date)? Can't have an event without the ending time, just like how Purple needs red.\n" +
                "   ____________________________________________________________\n");
        }
        String description = parts[0].replace("event ", "").trim()
            + " (from: " + fromAndTo[0].trim() + " to: "
            + fromAndTo[1].trim() + ")";
        String from = fromAndTo[0].trim();
        String to = fromAndTo[1].trim();
        return new Event(description, from, to);
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
                    "Here are the tasks in your list:   ");
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
                            newTask = gojo.handleToDos(userInput);
                            break;
                        case "deadline":
                            newTask = gojo.handleDeadlines(userInput);
                            break;
                        case "event":
                            newTask = gojo.handleEvents(userInput);
                            break;
                        default:
                            throw new InvalidCommandException();
                    }

                    items.add(newTask);
                    storage.addTask(newTask);
                    System.out.println("   ____________________________________________________________\n   Got it. I've added his task:\n      " +
                        newTask.showTask() + "\n   Now you have " + items.size() + " tasks in the list.\n"
                        + "   ____________________________________________________________\n");
                } catch (GojoException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

    }
}

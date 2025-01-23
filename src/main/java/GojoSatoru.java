import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.ArrayList;

public class GojoSatoru {
    public abstract static class Task {
        protected String task;
        protected Boolean completed;

        public Task(String task){
            this.task = task;
            this.completed = false;
        }

        public void markTask(){
            this.completed = true;
        }

        public void unmarkTask() {
            this.completed = false;
        }

        public abstract String showTask();
    }
    public class ToDo extends Task {
        public ToDo(String input) {
            super(input);
        }
        @Override
        public String showTask(){
            String output;
            if(this.completed) {
                output = "[T][X] " + task;
            }
            else{
                output = "[T][ ] " + task;
            }
            return output;
        }
    }
    public class Deadline extends Task {
        public Deadline(String input) {
            super(input);
        }
        @Override
        public String showTask(){
            String output;
            if(this.completed) {
                output = "[D][X] " + task;
            }
            else{
                output = "[D][ ] " + task;
            }
            return output;
        }
    }
    public class Event extends Task {
        public Event(String input) {
            super(input);
        }
        @Override
        public String showTask(){
            String output;
            if(this.completed) {
                output = "[E][X] " + task;
            }
            else{
                output = "[E][ ] " + task;
            }
            return output;
        }
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
    public static boolean isMarkOrUnmark(String input) {
        String[] words = input.split("\\s+");
        String firstWord = words[0];
        return firstWord.matches("mark") || firstWord.matches("unmark");
    }
    public static boolean isMark(String input) {
        String[] words = input.split("\\s+");
        String firstWord = words[0];
        return firstWord.matches("mark");
    }

    public static int getIndex(String input) {
        String[] words = input.split("\\s+");
        return Integer.parseInt(words[1])-1;
    }

    public ToDo handleToDos(String input){
        String[] words = input.split("\\s+");
        String taskName = String.join(" ", Arrays.copyOfRange(words, 1, words.length));
        return new ToDo(taskName);
    }

    public Deadline handleDeadlines(String input){
        String[] words = input.split("\\s+");
        String[] taskAndDeadline = String.join(" ", Arrays.copyOfRange(words, 1, words.length)).split("/by ");
        return new Deadline(taskAndDeadline[0] + "(by: " + taskAndDeadline[1] + ")");
    }

    public Event handleEvents(String input){
        String[] words = input.split("\\s+");
        String[] taskAndFrom = String.join(" ", Arrays.copyOfRange(words, 1, words.length)).split("/from ");
        String[] fromAndTo = taskAndFrom[1].split("/to ");
        return new Event(taskAndFrom[0] + "(from: " + fromAndTo[0] + "to: " + fromAndTo[1] + ")");
    }

    public static void main(String[] args) throws Exception {
        GojoSatoru gojo = new GojoSatoru();
        String introText = "   ____________________________________________________________\n" +
            "   Hello! I'm Gojo Satoru\n" +
            "   Am I the strongest chatbot because I'm Gojo Satoru\n" +
            "   or am I the Gojo Statoru because I am the weakest chatbot?\n" +
            "   What can I do for you?\n" + "   ____________________________________________________________\n";
        System.out.println(introText);
        Scanner userScanner = new Scanner(System.in);
        String userInput = userScanner.nextLine();
        ArrayList<Task> items = new ArrayList<>();
        while (!Objects.equals(userInput, "bye")){
            if (Objects.equals(userInput, "list")){
                System.out.println("   ____________________________________________________________\n   " +
                    "Here are the tasks in your list:   ");
                for (int i = 0; i < items.size(); i++) {
                    System.out.println("    " + Integer.toString(i + 1) + ". " + items.get(i).showTask());
                }
                System.out.println("   ____________________________________________________________\n");
            }
            else if (isMarkOrUnmark(userInput) && isSecondWordNumberAndInList(userInput, items.size())) {
                    Task pickedTask = items.get(getIndex(userInput));
                    if(isMark(userInput)){
                        pickedTask.markTask();
                        System.out.println("   ____________________________________________________________\n   " +
                            "Nice! I've marked this task as done: \n   " +
                            pickedTask.showTask() + "\n   ____________________________________________________________\n"
                            );
                    } else {
                        pickedTask.unmarkTask();
                        System.out.println("   ____________________________________________________________\n   " +
                            "OK, I've marked this task as not done yet: \n   " +
                            pickedTask.showTask() + "\n   ____________________________________________________________\n"
                        );
                    }
            }
            else {
                String typeOfTask = userInput.split("\\s+")[0];
                Task newTask;
                if (typeOfTask.equals( "todo") ) {
                    newTask = gojo.handleToDos(userInput);
                }
                else if (typeOfTask.equals("deadline")) {
                    newTask = gojo.handleDeadlines(userInput);
                }
                else if (typeOfTask.equals("event")){
                    newTask = gojo.handleEvents(userInput);
                }
                else {
                    Exception exception = new Exception("invalid task");
                    throw exception;
                }
                items.add(newTask);
                System.out.println("   ____________________________________________________________\n   Got it. I've added his task:\n      " +
                    newTask.showTask() + "\n   Now you have " + items.size() + " tasks in the list.\n"
                    + "   ____________________________________________________________\n");
            }
            userInput = userScanner.nextLine();
        }
        System.out.println( "   ____________________________________________________________\n  " +
            " You're weak... the next time I see you, I'd win.\n"
            + "   ____________________________________________________________\n");
    }
}

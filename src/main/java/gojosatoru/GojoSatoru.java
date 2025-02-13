package gojosatoru;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Scanner;

import gojosatoru.command.Command;
import gojosatoru.exceptions.GojoException;
import gojosatoru.parser.Parser;
import gojosatoru.storage.Storage;
import gojosatoru.tasks.TaskList;
import gojosatoru.ui.Ui;

/**
 * The `GojoSatoru` class is the main entry point for the application.
 * It initializes the necessary components and handles user input.
 * <p>
 * The class uses the following components:
 * - `Command` to handle various commands.
 * - `Storage` to load and save tasks.
 * - `Parser` to parse user input.
 * - `TaskList` to manage the list of tasks.
 * - `Ui` to handle user interactions.
 * <p>
 * The main method runs an infinite loop to continuously accept user input
 * until the "bye" command is given.
 */
public class GojoSatoru {
    private static final String FILE_PATH = "./src/main/data/cursedEnergy.txt";
    private static final Ui UI = new Ui();

    private static String inputDateFormat = "dd/MM/yyyy HHmm";
    private static String outputDateFormat = "MMM dd yyyy HHmm";
    private static DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(inputDateFormat);
    private static DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(outputDateFormat);
    private static Command command = new Command(inputFormatter, outputFormatter, inputDateFormat, UI);
    private static Storage storage = new Storage(FILE_PATH, command, inputFormatter, outputFormatter);
    private static Parser parser = new Parser(command);

    private static TaskList taskList;

    static {
        try {
            command.setStorage(storage);
            taskList = storage.load();
        } catch (IOException e) {
            UI.showStorageError();
        }
    }

    /**
     * Gets the response from the parser.
     *
     * @param input the user input
     * @return the response from the parser
     * @throws GojoException if an error occurs during parsing
     */
    public static String getResponse(String input) throws GojoException {
        try {
            return parser.parseCommand(input, taskList);
        } catch (GojoException e) {
            return e.getMessageForGui();
        }
    }
    /*
        * The main entry point for the application.
        * Waits for user input through the console and/or gui and displays the response.
     */
    public static void main(String[] args) throws Exception {
        Scanner userScanner = new Scanner(System.in);
        UI.showWelcome();
        while (true) {
            String userInput = userScanner.nextLine();
            assert userInput != null : "User input should not be null";
            try {
                getResponse(userInput);
                if (Objects.equals(userInput, "bye")) {
                    break;
                }
            } catch (GojoException e) {
                UI.showError(e.getMessage(), false);
            }
        }

    }
}

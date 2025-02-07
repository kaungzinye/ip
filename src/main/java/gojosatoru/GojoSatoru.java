package gojosatoru;


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
 *
 * The class uses the following components:
 * - `Command` to handle various commands.
 * - `Storage` to load and save tasks.
 * - `Parser` to parse user input.
 * - `TaskList` to manage the list of tasks.
 * - `Ui` to handle user interactions.
 *
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

    public static void main(String[] args) throws Exception {
        command.setStorage(storage);
        TaskList taskList = storage.load();
        Scanner userScanner = new Scanner(System.in);
        UI.showWelcome();
        while (true) {
            String userInput = userScanner.nextLine();
            try {
                parser.parseCommand(userInput, taskList);
                if (Objects.equals(userInput, "bye")) {
                    break;
                }
            } catch (GojoException e) {
                UI.showError(e.getMessage());
            }
        }

    }
}

package gojosatoru;

import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Scanner;

import gojosatoru.exceptions.*;
import gojosatoru.command.Command;
import gojosatoru.parser.Parser;
import gojosatoru.storage.*;
import gojosatoru.tasks.TaskList;
import gojosatoru.ui.Ui;


public class GojoSatoru {
    private static final String FILE_PATH = "./src/main/data/cursedEnergy.txt";
    private static final Ui UI = new Ui();

    private static String inputDateFormat = "dd/MM/yyyy HHmm";
    private static String outputDateFormat = "MMM dd yyyy HHmm";
    private static DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(inputDateFormat);
    private static DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(outputDateFormat);
    private static Command command = new Command(inputFormatter, outputFormatter, inputDateFormat, UI);
    private static Storage storage = new Storage(FILE_PATH, inputFormatter, outputFormatter);
    private static Parser parser = new Parser(command);

    public static void main(String[] args) throws Exception {
        command.setStorage(storage);
        TaskList taskList = storage.load();
        Scanner userScanner = new Scanner(System.in);
        UI.showWelcome();
        while (true){
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

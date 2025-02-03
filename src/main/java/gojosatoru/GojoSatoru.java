package gojosatoru;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Scanner;
import java.util.ArrayList;
import gojosatoru.exceptions.*;
import gojosatoru.handlers.TaskHandler;
import gojosatoru.parser.Parser;
import gojosatoru.storage.*;
import gojosatoru.tasks.Task;
import gojosatoru.tasks.TaskList;
import gojosatoru.ui.Ui;


public class GojoSatoru {
    private static final String FILE_PATH = "./src/main/data/cursedEnergy.txt";
    private static final Ui UI = new Ui();

    private static String inputDateFormat = "dd/MM/yyyy HHmm";
    private static String outputDateFormat = "MMM dd yyyy HHmm";
    private static DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(inputDateFormat);
    private static DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(outputDateFormat);
    private static TaskHandler taskHandler = new TaskHandler(inputFormatter, outputFormatter, inputDateFormat);
    private static Storage storage = new Storage(FILE_PATH, taskHandler, inputFormatter, outputFormatter);
    private static Parser parser = new Parser(taskHandler, storage, UI);

    public static void main(String[] args) throws Exception {
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

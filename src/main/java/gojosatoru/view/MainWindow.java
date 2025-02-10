package gojosatoru.view;

import gojosatoru.GojoSatoru;
import gojosatoru.exceptions.GojoException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private GojoSatoru gojoSatoru;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    private Runnable onByeInput;

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Gojo instance */
    public void setGojo(GojoSatoru gojoSatoru) {
        this.gojoSatoru = gojoSatoru;
    }
    /**
     * Sets the consumer to be called when the user inputs "bye".
     *
     * @param onByeInput the consumer to be called
     */
    public void setOnByeInput(Runnable onByeInput) {
        this.onByeInput = onByeInput;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() throws GojoException {
        String input = userInput.getText();
        String response;
        try {
            response = gojoSatoru.getResponse(input);
        } catch (GojoException e) {
            response = e.getMessage();
        }
        dialogContainer.getChildren().addAll(
            DialogBox.getUserDialog(input, userImage),
            DialogBox.getDukeDialog(response, dukeImage)
        );
        userInput.clear();

        if (input.equals("bye") && onByeInput != null) {
            onByeInput.run();
        }
    }
}

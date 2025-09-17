package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import nailong.Nailong;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput; //where users type their messages
    @FXML
    private Button sendButton;
    @FXML
    private Label greetingLabel;

    private Nailong nailong;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.png"));
    private final Image nailongImage = new Image(this.getClass().getResourceAsStream("/images/Nailong.png"));

    /**
     * Initializes the controller after its root element has been completely loaded.
     * <p>
     * This method is automatically called by the FXMLLoader once all @FXML-annotated
     * fields have been injected. It performs the following setup tasks:
     * <ul>
     *   <li>Binds the vertical scroll value of the scroll pane to the height of the
     *       dialog container, ensuring the view scrolls automatically to the newest
     *       dialog entry.</li>
     * </ul>
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Nailong instance */
    public void setNailong(Nailong n) {
        nailong = n;
        greetingMessage();
    }

    /**
     * Creates two dialog boxes, one echoing user input
     * and the other containing Nailong's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText(); //Get text from TextField
        String response = nailong.getResponse(input); // Pass the input to getResponse
        //Display both user message and bot response
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getNailongDialog(response, nailongImage)
        );
        userInput.clear();
    }
    @FXML
    private void greetingMessage() {
        String input = nailong.getWelcomeMessage();
        dialogContainer.getChildren().addAll(
                DialogBox.getNailongDialog(input, nailongImage)
        );
    }
}

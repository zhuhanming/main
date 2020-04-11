package modulo.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import modulo.logic.Logic;
import modulo.logic.commands.CommandResult;
import modulo.logic.commands.exceptions.CommandException;
import modulo.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String DEFAULT_PLACEHOLDER_TEXT = "Enter command here...";
    public static final String ERROR_STYLE_CLASS = "error";
    public static final String STATEFUL_STYLE_CLASS = "stateful";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;

    @FXML
    private TextField commandTextField;

    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToIndicateSuccess());
    }

    /**
     * Updates the placeholder text of the user input field.
     */
    public void updatePlaceholder(String newPlaceholderText) {
        commandTextField.setPromptText(newPlaceholderText);
        if (newPlaceholderText.equals(DEFAULT_PLACEHOLDER_TEXT)) {
            setStyleToIndicateStatelessCommand();
        } else {
            setStyleToIndicateStatefulCommand();
        }
    }

    /**
     * Returns the current placeholder text of the user input field.
     */
    public String getPlaceholder() {
        return commandTextField.getPromptText();
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        try {
            commandExecutor.execute(commandTextField.getText());
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Sets the command box style to use the style for an error-less command.
     */
    private void setStyleToIndicateSuccess() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to use the style for a stateful command.
     */
    private void setStyleToIndicateStatelessCommand() {
        commandTextField.getStyleClass().remove(STATEFUL_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();
        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }
        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a stateful command.
     */
    private void setStyleToIndicateStatefulCommand() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();
        if (styleClass.contains(STATEFUL_STYLE_CLASS)) {
            return;
        }
        styleClass.add(STATEFUL_STYLE_CLASS);
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}

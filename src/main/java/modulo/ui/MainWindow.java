package modulo.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import modulo.commons.core.GuiSettings;
import modulo.commons.core.LogsCenter;
import modulo.commons.core.index.Index;
import modulo.logic.Logic;
import modulo.logic.commands.CommandResult;
import modulo.logic.commands.exceptions.CommandException;
import modulo.logic.parser.exceptions.ParseException;
import modulo.model.displayable.DisplayableType;
import modulo.model.event.Event;

/**
 * The Main Window. Provides the basic application layout containing a menu bar and space where other JavaFX elements
 * can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private ListPanel listPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private DescriptionWindow descriptionWindow;
    private RightPanel rightPanel;
    private CommandBox commandBox;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private HBox menuBar;

    @FXML
    private Button moduleButton;

    @FXML
    private Button eventButton;

    @FXML
    private Button helpButton;

    @FXML
    private StackPane listPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane slideWindowListPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private StackPane slideWindowEventPlaceholder;


    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        helpWindow = new HelpWindow();
        descriptionWindow = new DescriptionWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Fills up all the placeholders of this window.
     */
    public void fillInnerParts() {
        //retrieve the filtered list of module or event.
        listPanel = new ListPanel(logic.getFilteredFocusedList(), this);
        listPanelPlaceholder.getChildren().add(listPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getModuloFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        rightPanel = new RightPanel(logic.getFocusedDisplayable(), this);
        slideWindowListPlaceholder.getChildren().add(rightPanel.getRoot());

        commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        if (logic.getFilteredFocusedList().size() == 0) {
            moduleButton.getStyleClass().add("active");
            resultDisplay.setFeedbackToUser("Use the module command to get started!");
        } else if (logic.getFilteredFocusedList().get(0) instanceof Event) {
            eventButton.getStyleClass().add("active");
        } else {
            moduleButton.getStyleClass().add("active");
        }

    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    /**
     * Opens the description window with the given description.
     *
     * @param description Description to show.
     */
    public void handleDescription(String description, String moduleName) {
        if (descriptionWindow.isShowing()) {
            descriptionWindow.hide();
        }
        descriptionWindow = new DescriptionWindow(description, moduleName);
        descriptionWindow.show();
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
        descriptionWindow.hide();
    }

    public ListPanel getListPanel() {
        return listPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
            if (commandResult.isToExit()) {
                handleExit();
            }
            if (commandResult.isToShowHelp()) {
                handleHelp();
            }
            if (commandResult.isToUpdateLeftPanel()) {
                updateLeftPanel();
            }
            if (commandResult.isToUpdateRightPanel()) {
                updateRightPanel();
            }
            if (commandResult.getIndexToShow() != null) {
                listPanel.selectDisplayable(commandResult.getIndexToShow());
            }
            if (commandResult.getNewPlaceholderText() != null) {
                commandBox.updatePlaceholder(commandResult.getNewPlaceholderText());
            } else if (!commandBox.getPlaceholder().equals(CommandBox.DEFAULT_PLACEHOLDER_TEXT)) {
                commandBox.updatePlaceholder(CommandBox.DEFAULT_PLACEHOLDER_TEXT);
            }
            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Shows event list as callback from EventButton.
     */
    @FXML
    public void handleEventButton() {
        try {
            this.executeCommand("list e");
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: list e");
            resultDisplay.setFeedbackToUser(e.getMessage());
        }
    }

    /**
     * Shows module list as callback from ModuleButton.
     */
    @FXML
    public void handleModuleButton() {
        try {
            this.executeCommand("list m");
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: list m");
            resultDisplay.setFeedbackToUser(e.getMessage());
        }
    }

    /**
     * Updates the left panel of the application.
     */
    public void updateLeftPanel() {
        listPanel = new ListPanel(logic.getFilteredFocusedList(), this);
        listPanelPlaceholder.getChildren().clear();
        listPanelPlaceholder.getChildren().add(listPanel.getRoot());
        if (logic.getCurrentDisplayableType() == DisplayableType.EVENT) {
            moduleButton.getStyleClass().clear();
            moduleButton.getStyleClass().add("menuBarButton");
            eventButton.getStyleClass().clear();
            eventButton.getStyleClass().addAll("menuBarButton", "active");
        } else {
            assert logic.getCurrentDisplayableType() == DisplayableType.MODULE;
            eventButton.getStyleClass().clear();
            eventButton.getStyleClass().add("menuBarButton");
            moduleButton.getStyleClass().clear();
            moduleButton.getStyleClass().addAll("menuBarButton", "active");
        }
    }

    /**
     * Shows the deadline list on the right panel.
     */
    public void updateRightPanel() {
        rightPanel = new RightPanel(logic.getFocusedDisplayable(), this);
        slideWindowListPlaceholder.getChildren().clear();
        slideWindowListPlaceholder.getChildren().add(rightPanel.getRoot());
    }

    /**
     * Shows the displayable at given index on the right panel.
     *
     * @param index Index of displayable to display.
     */
    public void handleListClick(int index) {
        try {
            this.executeCommand("view " + (index + 1));
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: view " + (index + 1));
            resultDisplay.setFeedbackToUser(e.getMessage());
        }
    }

    /**
     * Shows the event being clicked.
     *
     * @param event Event to display.
     */
    public void handleSidePanelListClick(Event event) {
        try {
            this.executeCommand("list e");
            Index index = this.listPanel.getEventIndex(event);
            if (index == null) {
                this.executeCommand("list all e");
                index = this.listPanel.getEventIndex(event);
            }
            assert index != null;
            this.executeCommand("view " + index.getOneBased());
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: list e + view index");
            resultDisplay.setFeedbackToUser(e.getMessage());
        }
    }

    /**
     * Executes the done command for the given one-based index.
     *
     * @param index One-based index of deadline.
     */
    public void handleDeadlineClick(int index) {
        try {
            this.executeCommand("done " + index);
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: done " + (index + 1));
            resultDisplay.setFeedbackToUser(e.getMessage());
        }
    }
}

package seedu.address.ui;

import java.util.List;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.deadline.Deadline;
import seedu.address.model.event.Event;

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
    private SlideWindowDeadlineList slideWindowDeadlineList;
    //private SlideWindowEvent slideWindowEvent;

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

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        // setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     *
     * @param keyCombination the KeyCombination value of the accelerator
     */
    //private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
    //    menuItem.setAccelerator(keyCombination);

    /*
     * TODO: the code below can be removed once the bug reported here
     * https://bugs.openjdk.java.net/browse/JDK-8131666
     * is fixed in later version of SDK.
     *
     * According to the bug report, TextInputControl (TextField, TextArea) will
     * consume function-key events. Because CommandBox contains a TextField, and
     * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
     * not work when the focus is in them because the key event is consumed by
     * the TextInputControl(s).
     *
     * For now, we add following event filter to capture such key events and open
     * help window purposely so to support accelerators even when focus is
     * in CommandBox or ResultDisplay.
     */
    //    getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
    //        if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
    //            menuItem.getOnAction().handle(new ActionEvent());
    //            event.consume();
    //        }
    //    });
    //}

    /**
     * Fills up all the placeholders of this window.
     */
    public void fillInnerParts() {
        //retrieve the filtered list of module or event.
        listPanel = new ListPanel(logic.getFilteredFocusedList(), this);
        listPanelPlaceholder.getChildren().add(listPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getCalendarFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        slideWindowDeadlineList = new SlideWindowDeadlineList(logic.getFilteredEvent(), null, null);
        slideWindowListPlaceholder.getChildren().add(slideWindowDeadlineList.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
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
    }

    public ListPanel getListPanel() {
        return listPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    public CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
            if (commandResult.isEventList() && commandResult.getSlideWindowEvent() == null) {
                showEventList();
            } else if (commandResult.isModuleList() && commandResult.getSlideWindowEvent() == null) {
                showModuleList();
            } else if (commandResult.isShowHelp()) {
                handleHelp();
            } else if (commandResult.isExit()) {
                handleExit();
            } else if (commandResult.getSlideWindowEvent() != null) {
                if (commandResult.getSlideWindowDeadlineList() != null
                        && commandResult.getSlideWindowEventList() == null) {
                    showRightPanelEvent(commandResult.getSlideWindowDeadlineList());
                } else if (commandResult.getSlideWindowEventList() != null
                        && commandResult.getSlideWindowDeadlineList() == null) {
                    showRightPanelModule(commandResult.getSlideWindowEventList());
                }
                listPanel.selectDisplayable(commandResult.getIndexToShow());
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
     *
     * @throws CommandException If command is not recognised, which should not happen.
     * @throws ParseException   If the command cannot be parsed, which should not happen.
     */
    public void handleEventButton() throws CommandException, ParseException {
        this.executeCommand("list e");
        this.showEventList();
    }

    /**
     * Shows module list as callback from ModuleButton.
     *
     * @throws CommandException If command is not recognised, which should not happen.
     * @throws ParseException   If the command cannot be parsed, which should not happen.
     */
    public void handleModuleButton() throws CommandException, ParseException {
        this.executeCommand("list m");
        this.showModuleList();
    }

    /**
     * Shows the list of module events on the application.
     */
    public void showEventList() {
        listPanel = new ListPanel(logic.getFilteredFocusedList(), this);
        listPanelPlaceholder.getChildren().clear();
        listPanelPlaceholder.getChildren().add(listPanel.getRoot());
        moduleButton.getStyleClass().clear();
        moduleButton.getStyleClass().add("menuBarButton");
        eventButton.getStyleClass().clear();
        eventButton.getStyleClass().addAll("menuBarButton", "active");
    }

    /**
     * Shows the list of modules on the application.
     */
    public void showModuleList() {
        listPanel = new ListPanel(logic.getFilteredFocusedList(), this);
        listPanelPlaceholder.getChildren().clear();
        listPanelPlaceholder.getChildren().add(listPanel.getRoot());
        eventButton.getStyleClass().clear();
        eventButton.getStyleClass().add("menuBarButton");
        moduleButton.getStyleClass().clear();
        moduleButton.getStyleClass().addAll("menuBarButton", "active");
    }

    /**
     * Shows the deadline list on the right panel.
     *
     * @param deadlineList Deadline list to show.
     */
    public void showRightPanelEvent(List<Deadline> deadlineList) {
        slideWindowDeadlineList = new SlideWindowDeadlineList(logic.getFilteredEvent(), deadlineList, null);
        slideWindowListPlaceholder.getChildren().clear();
        slideWindowListPlaceholder.getChildren().add(slideWindowDeadlineList.getRoot());
    }

    /**
     * Shows the event list on the right panel.
     *
     * @param eventsList Event list to show.
     */
    public void showRightPanelModule(List<Event> eventsList) {
        slideWindowDeadlineList = new SlideWindowDeadlineList(logic.getFilteredEvent(), null, eventsList);
        slideWindowListPlaceholder.getChildren().clear();
        slideWindowListPlaceholder.getChildren().add(slideWindowDeadlineList.getRoot());
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
            logger.info("Invalid command: view" + (index + 1));
            resultDisplay.setFeedbackToUser(e.getMessage());
        }
    }
}

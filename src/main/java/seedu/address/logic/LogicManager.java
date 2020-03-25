package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddModuleCommandResult;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CalendarParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Displayable;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyCalendar;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final CalendarParser calendarParser;
    private final AddModuleStatefulLogicManager addModuleStatefulLogicManager;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        calendarParser = new CalendarParser();
        addModuleStatefulLogicManager = new AddModuleStatefulLogicManager(model);
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {

        for (int i = 0; i < model.getCalendar().getEventList().size(); i++) {
            System.out.println("Item " + i + ": ");
            System.out.println(model.getCalendar().getEventList().get(i));
        }
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;

        if (addModuleStatefulLogicManager.hasState()) {
            commandResult = addModuleStatefulLogicManager.execute(commandText);
        } else {
            Command command = calendarParser.parseCommand(commandText);
            commandResult = command.execute(model);
            System.out.println("Command Result " + (commandResult instanceof AddModuleCommandResult));
            if (commandResult instanceof AddModuleCommandResult) {
                AddModuleCommandResult castedCommandResult = (AddModuleCommandResult) commandResult;
                this.addModuleStatefulLogicManager.setState(castedCommandResult.getModule(),
                        castedCommandResult.getEventTypes());
                System.out.println(castedCommandResult.getEventTypes());
            }
        }

        try {
            storage.saveCalendar(model.getCalendar());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyCalendar getCalendar() {
        return model.getCalendar();
    }

    @Override
    public ObservableList<? extends Displayable> getFilteredFocusedList() {
        return model.getFilteredFocusedList();
    }

    @Override
    public Path getCalendarFilePath() {
        return model.getCalendarFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}

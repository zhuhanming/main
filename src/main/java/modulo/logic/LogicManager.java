package modulo.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import modulo.commons.core.GuiSettings;
import modulo.commons.core.LogsCenter;
import modulo.logic.commands.AddModuleCommandResult;
import modulo.logic.commands.Command;
import modulo.logic.commands.CommandResult;
import modulo.logic.commands.exceptions.CommandException;
import modulo.logic.parser.ModuloParser;
import modulo.logic.parser.exceptions.ParseException;
import modulo.model.Model;
import modulo.model.ReadOnlyModulo;
import modulo.model.displayable.Displayable;
import modulo.model.displayable.DisplayableType;
import modulo.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final ModuloParser moduloParser;
    private final AddModuleStatefulLogicManager addModuleStatefulLogicManager;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        moduloParser = new ModuloParser();
        addModuleStatefulLogicManager = new AddModuleStatefulLogicManager(model);
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {

        for (int i = 0; i < model.getModulo().getEventList().size(); i++) {
            System.out.println("Item " + i + ": ");
            System.out.println(model.getModulo().getEventList().get(i));
        }
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;

        if (addModuleStatefulLogicManager.hasState()) {
            commandResult = addModuleStatefulLogicManager.execute(commandText);
        } else {
            Command command = moduloParser.parseCommand(commandText);
            commandResult = command.execute(model);
            if (commandResult instanceof AddModuleCommandResult) {
                AddModuleCommandResult castedCommandResult = (AddModuleCommandResult) commandResult;
                this.addModuleStatefulLogicManager.setState(castedCommandResult.getModule(),
                        castedCommandResult.getEventTypes());
                model.setFocusedDisplayable(castedCommandResult.getModule());
            }
        }

        try {
            storage.saveCalendar(model.getModulo());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyModulo getModulo() {
        return model.getModulo();
    }

    @Override
    public ObservableList<? extends Displayable> getFilteredFocusedList() {
        return model.getFilteredFocusedList();
    }

    @Override
    public Displayable getFocusedDisplayable() {
        return model.getFocusedDisplayable();
    }

    @Override
    public DisplayableType getCurrentDisplayableType() {
        return model.getCurrentDisplayableType();
    }

    @Override
    public Path getModuloFilePath() {
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

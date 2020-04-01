package modulo.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import modulo.commons.core.GuiSettings;
import modulo.logic.commands.CommandResult;
import modulo.logic.commands.exceptions.CommandException;
import modulo.logic.parser.exceptions.ParseException;
import modulo.model.Displayable;
import modulo.model.DisplayableType;
import modulo.model.Model;
import modulo.model.ReadOnlyModulo;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns Modulo.
     *
     * @see Model#getModulo()
     */
    ReadOnlyModulo getModulo();

    /**
     * Returns an unmodifiable view of the filtered list of focused items. This can be either {@code Module} or {@code}
     * Event.
     */
    ObservableList<? extends Displayable> getFilteredFocusedList();

    Displayable getFocusedDisplayable();

    /**
     * Returns the DisplayableType of the list that is currently displayed.
     */
    DisplayableType getCurrentDisplayableType();

    /**
     * Returns the user prefs' modulo file path.
     */
    Path getModuloFilePath();

    /**
     * Returns the user prefs' GUI settings.
     *
     * @return GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}

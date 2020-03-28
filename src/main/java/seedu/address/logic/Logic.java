package seedu.address.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Displayable;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyCalendar;

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
     * Returns the AddressBook.
     *
     * @see Model#getCalendar()
     */
    ReadOnlyCalendar getCalendar();

    /**
     * Returns an unmodifiable view of the filtered list of focused items. This can be either {@code Module} or {@code}
     * Event.
     */
    ObservableList<? extends Displayable> getFilteredFocusedList();

    Displayable getFilteredEvent();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getCalendarFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}

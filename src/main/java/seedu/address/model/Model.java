package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.entity.CalendarItem;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<CalendarItem> PREDICATE_SHOW_ALL_CALENDAR_ITEMS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getCalendarFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setCalendarFilePath(Path calendarFilePath);

    /**
     * Finds reference to existing calendar item
     */

     CalendarItem findCalendarItem(CalendarItem calendarItem);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setCalendar(ReadOnlyCalendar calendar);

    /** Returns the AddressBook */
    ReadOnlyCalendar getCalendar();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasCalendarItem(CalendarItem calendarItem);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deleteCalendarItem(CalendarItem calendarItem);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addCalendarItem(CalendarItem calendarItem);

    void addModule(Module module );

    void hasModule(Module module);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setCalendarItem(CalendarItem target, CalendarItem editedCalendarItem);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<CalendarItem> getFilteredCalendarItemList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredCalendarItemList(Predicate<CalendarItem> predicate);


}

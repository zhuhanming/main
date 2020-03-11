package seedu.address.model;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.entity.CalendarItem;
import seedu.address.model.entity.Event;
import seedu.address.model.entity.Module;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<CalendarItem> PREDICATE_SHOW_ALL_CALENDAR_ITEMS = unused -> true;
    Predicate<Module> PREDICATE_SHOW_ALL_MODULES = unused -> true;

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
     * Finds reference to existing calendar item
     */
    Module findModule(Module module);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setCalendar(ReadOnlyCalendar calendar);

    /**
     * Returns the AddressBook
     */
    ReadOnlyCalendar getCalendar();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasCalendarItem(CalendarItem calendarItem);

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasModule(Module module);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deleteCalendarItem(CalendarItem calendarItem);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deleteModule(Module module);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addCalendarItem(CalendarItem calendarItem);

    void addModule(Module module);


    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setCalendarItem(CalendarItem target, CalendarItem editedCalendarItem);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setModule(Module target, Module editedModule);

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<CalendarItem> getFilteredCalendarItemList();

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Module> getFilteredModuleList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredCalendarItemList(Predicate<CalendarItem> predicate);

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredModuleList(Predicate<Module> predicate);

    //to be deleted after debugging
    String checkCurrentCalendar();

    List<Event> findAllEvents(Event toFind);
}

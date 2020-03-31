package modulo.model;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import modulo.commons.core.GuiSettings;
import modulo.model.event.Event;
import modulo.model.module.AcademicYear;
import modulo.model.module.Module;
import modulo.model.module.ModuleCode;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Event> PREDICATE_SHOW_UPCOMING_EVENTS = event -> !event
            .getEventStart().toLocalDate().isBefore(LocalDate.now());
    Predicate<Event> PREDICATE_SHOW_ALL_EVENTS = unused -> true;
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
    Event findEvent(Event event);

    /**
     * Finds reference to existing calendar item
     */
    Module findModule(Module module);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setModulo(ReadOnlyModulo modulo);

    /**
     * Returns the AddressBook
     */
    ReadOnlyModulo getModulo();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasEvent(Event event);

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasModule(ModuleCode moduleCode, AcademicYear academicYear);

    /**
     * Deletes the given person. The person must exist in the address book.
     */
    void deleteEvent(Event event);

    /**
     * Deletes the given person. The person must exist in the address book.
     */
    void deleteModule(Module module);

    /**
     * Adds the given person. {@code person} must not already exist in the address book.
     */
    void addEvent(Event event);

    void addModule(ModuleCode moduleCode, AcademicYear academicYear);

    Optional<Module> getModule(ModuleCode moduleCode, AcademicYear academicYear);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}. {@code target} must exist in the address
     * book. The person identity of {@code editedPerson} must not be the same as another existing person in the address
     * book.
     */
    void setEvent(Event target, Event editedEvent);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}. {@code target} must exist in the address
     * book. The person identity of {@code editedPerson} must not be the same as another existing person in the address
     * book.
     */
    void setModule(Module target, Module editedModule);

    /**
     * Returns an unmodifiable view of the filtered event list
     */
    ObservableList<Event> getFilteredEventList();

    /**
     * Returns an unmodifiable view of the filtered module list
     */
    ObservableList<Module> getFilteredModuleList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEventList(Predicate<Event> predicate);

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredModuleList(Predicate<Module> predicate);

    /**
     * Returns the filtered list that is in focus.
     *
     * @return List in focus.
     */
    ObservableList<? extends Displayable> getFilteredFocusedList();


    Displayable getFocusedDisplayable();

    /**
     * Set the list in focus.
     */
    void setFilteredFocusedList(DisplayableType displayableType);

    /**
     * Sets the displayable to be focused on.
     *
     * @param displayable Displayable to focus on.
     */
    void setFocusedDisplayable(Displayable displayable);

    /**
     * Returns the DisplayableType of the list that is currently displayed.
     */
    DisplayableType getCurrentDisplayableType();

    public void setFilteredFocusedListShowAll(DisplayableType displayableType);

    boolean isSameFocusedDisplayable(Displayable displayable);

    void updateFilteredDisplayableList(Predicate<Displayable> predicate);

    Object[] getFilteredDisplayableList(Predicate<Displayable> predicate);

    void unsetFocusedDisplayable();

    //to be deleted after debugging
    String checkCurrentCalendar();

    List<Event> findAllEvents(Event toFind);

    Event getEvent(Event toFind);
}

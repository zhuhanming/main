package modulo.model;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import modulo.commons.core.GuiSettings;
import modulo.model.displayable.Displayable;
import modulo.model.displayable.DisplayableType;
import modulo.model.event.Event;
import modulo.model.module.AcademicYear;
import modulo.model.module.Module;
import modulo.model.module.ModuleCode;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicates} for the two lists.
     */
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
     * Returns the user prefs' Modulo file path.
     */
    Path getModuloFilePath();

    /**
     * Sets the user prefs' Modulo file path.
     */
    void setModuloFilePath(Path moduloFilePath);

    /**
     * Finds reference to existing event.
     */
    Event findEvent(Event event);

    /**
     * Finds and returns all events that are similar to the event to find.
     *
     * @param toFind Event to find.
     * @return List of all events.
     */
    List<Event> findAllEvents(Event toFind);

    /**
     * Finds reference to existing module.
     */
    Module findModule(Module module);

    /**
     * Replaces Modulo data with the data in {@code modulo}.
     */
    void setModulo(ReadOnlyModulo modulo);

    /**
     * Returns Modulo.
     */
    ReadOnlyModulo getModulo();

    /**
     * Returns true if an event with the same identity as {@code event} exists in Modulo.
     */
    boolean hasEvent(Event event);

    /**
     * Returns true if a module with the given {@code moduleCode} and {@code academicYear} exists in Modulo.
     */
    boolean hasModule(ModuleCode moduleCode, AcademicYear academicYear);

    /**
     * Deletes the given event. The event must exist in Modulo.
     */
    void deleteEvent(Event event);

    /**
     * Deletes the given module. The module must exist in Modulo.
     */
    void deleteModule(Module module);

    /**
     * Adds the given event. {@code event} must not already exist in Modulo.
     */
    void addEvent(Event event);

    /**
     * Adds a module to Modulo using the given {@code moduleCode} and {@code academicYear}.
     *
     * @param moduleCode   Module code of the module.
     * @param academicYear Academic year of the module.
     */
    void addModule(ModuleCode moduleCode, AcademicYear academicYear);

    /**
     * Gets an optional for the module with the given module code and academic year.
     *
     * @param moduleCode   Module code to look for.
     * @param academicYear Academic year of the module.
     * @return Optional of the required module.
     */
    Optional<Module> getModule(ModuleCode moduleCode, AcademicYear academicYear);

    /**
     * Replaces the given event {@code target} with {@code editedEvent}. {@code target} must exist in Modulo. The event
     * identity of {@code editedEvent} must not be the same as another existing event in Modulo.
     */
    void setEvent(Event target, Event editedEvent);

    /**
     * Replaces the given module {@code target} with {@code editedModule}. {@code target} must exist in Modulo. The
     * module identity of {@code editedModule} must not be the same as another existing module in Modulo.
     */
    void setModule(Module target, Module editedModule);

    /**
     * Returns an unmodifiable view of the filtered event list.
     */
    ObservableList<Event> getFilteredEventList();

    /**
     * Returns an unmodifiable view of the filtered module list.
     */
    ObservableList<Module> getFilteredModuleList();

    /**
     * Updates the filter of the filtered event list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEventList(Predicate<Event> predicate);

    /**
     * Updates the filter of the filtered module list to filter by the given {@code predicate}.
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

    /**
     * Gets the displayable type that is in focus.
     *
     * @return Displayable type that is in focus.
     */
    Displayable getFocusedDisplayable();

    /**
     * Sets the type of list to be in focus.
     *
     * @param displayableType Type of list to be shown.
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

    /**
     * Checks if the focused displayable is the same as {@code displayable}.
     *
     * @param displayable Displayable to check.
     * @return Boolean value denoting whether the focused displayable is the same as the one required.
     */
    boolean isSameFocusedDisplayable(Displayable displayable);

    /**
     * Set a filter for the current displayable list.
     *
     * @param predicate Predicate to filter with.
     */
    void updateFilteredDisplayableList(Predicate<Displayable> predicate);

    /**
     * Returns the filtered displayable list.
     *
     * @param predicate Predicate to filter with.
     * @return List of the displayable with the filter applied.
     */
    Object[] getFilteredDisplayableList(Predicate<Displayable> predicate);

    /**
     * Unsets the current focused displayable.
     */
    void unsetFocusedDisplayable();

    //to be deleted after debugging
    String checkCurrentCalendar();
}

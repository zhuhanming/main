package modulo.model;

import static java.util.Objects.requireNonNull;
import static modulo.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import modulo.commons.core.GuiSettings;
import modulo.commons.core.LogsCenter;
import modulo.model.event.Event;
import modulo.model.module.AcademicYear;
import modulo.model.module.Module;
import modulo.model.module.ModuleCode;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Modulo modulo;
    private final UserPrefs userPrefs;
    private final FilteredList<Event> filteredEvents;
    private final FilteredList<Module> filteredModules;
    private FilteredList<? extends Displayable> focusedFilteredDisplayables;
    private Displayable focusedDisplayable;
    private DisplayableType currentDisplayableType;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyModulo calendar, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(calendar, userPrefs);

        logger.fine("Initializing with calendar: " + calendar + " and user prefs " + userPrefs);
        this.modulo = new Modulo(calendar);
        this.userPrefs = new UserPrefs(userPrefs);


        filteredEvents = new FilteredList<>(this.modulo.getEventList());
        filteredModules = new FilteredList<>(this.modulo.getModuleList());
        setFilteredFocusedList(DisplayableType.MODULE);
        currentDisplayableType = DisplayableType.MODULE;
    }

    public ModelManager() {
        this(new Modulo(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getCalendarFilePath() {
        return userPrefs.getCalendarFilePath();
    }

    @Override
    public void setCalendarFilePath(Path calendarFilePath) {
        requireNonNull(calendarFilePath);
        userPrefs.setCalendarFilePath(calendarFilePath);
    }

    //=========== Modulo ================================================================================

    @Override
    public void setModulo(ReadOnlyModulo modulo) {
        this.modulo.resetData(modulo);
    }

    @Override
    public ReadOnlyModulo getModulo() {
        return modulo;
    }

    @Override
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return modulo.hasEvent(event);
    }

    @Override
    public void deleteEvent(Event target) {
        modulo.removeEvent(target);
    }

    @Override
    public void addEvent(Event calendarItem) {
        modulo.addEvent(calendarItem);
        updateFilteredEventList(PREDICATE_SHOW_UPCOMING_EVENTS);
    }


    @Override
    public void setEvent(Event target, Event editedCalendarItem) {
        requireAllNonNull(target, editedCalendarItem);
        modulo.setEvent(target, editedCalendarItem);
    }

    @Override
    public Event findEvent(Event toFind) {
        requireNonNull(toFind);
        List<Event> eventList = modulo.getEventList();
        for (Event event : eventList) {
            if (event.isSameEvent(toFind)) {
                return event;
            }
        }
        return null;
    }

    @Override
    public List<Event> findAllEvents(Event toFind) {
        requireNonNull(toFind);
        List<Event> eventList = modulo.getEventList();
        List<Event> result = new ArrayList<>();
        System.out.println("event list length: " + eventList.size());
        for (Event event : eventList) {
            if (event.isSameCategoryOfEvents(toFind)) {
                result.add(event);
            }
        }
        return result;
    }

    @Override
    public Event getEvent(Event toFind) {
        requireNonNull(toFind);
        return modulo.getEvent(toFind);
    }

    @Override
    public boolean hasModule(ModuleCode moduleCode, AcademicYear academicYear) {
        requireAllNonNull(moduleCode, academicYear);
        return modulo.hasModule(moduleCode, academicYear);
    }

    @Override
    public void deleteModule(Module target) {
        modulo.removeModule(target);
    }

    @Override
    public void addModule(ModuleCode moduleCode, AcademicYear academicYear) {
        modulo.addModule(moduleCode, academicYear);
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public Optional<Module> getModule(ModuleCode moduleCode, AcademicYear academicYear) {
        return modulo.getModule(moduleCode, academicYear);
    }


    @Override
    public void setModule(Module target, Module editedModule) {
        requireAllNonNull(target, editedModule);

        modulo.setModule(target, editedModule);
    }

    @Override
    public Module findModule(Module toFind) {
        requireNonNull(toFind);
        List<Module> moduleList = modulo.getModuleList();
        for (Module module : moduleList) {
            if (module.matchModule(toFind)) {
                return module;
            }
        }
        return null;
    }

    @Override
    public DisplayableType getCurrentDisplayableType() {
        return currentDisplayableType;
    }

    //=========== Setting Focus =============================================================

    @Override
    public void setFocusedDisplayable(Displayable displayable) {
        boolean hasDisplayable = false;
        if (displayable instanceof Module) {
            hasDisplayable =
                    hasModule(((Module) displayable).getModuleCode(), ((Module) displayable).getAcademicYear());
        } else if (displayable instanceof Event) {
            hasDisplayable = hasEvent((Event) displayable);
        }
        if (!hasDisplayable) {
            return;
        }
        currentDisplayableType = displayable instanceof Module ? DisplayableType.MODULE : DisplayableType.EVENT;
        focusedDisplayable = displayable;
    }

    @Override
    public void unsetFocusedDisplayable() {
        focusedDisplayable = null;
    }


    @Override
    public void setFilteredFocusedList(DisplayableType displayableType) {
        if (displayableType == DisplayableType.EVENT) {
            updateFilteredEventList(PREDICATE_SHOW_UPCOMING_EVENTS);
            focusedFilteredDisplayables = filteredEvents;
        } else if (displayableType == DisplayableType.MODULE) {
            updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
            focusedFilteredDisplayables = filteredModules;
        }
    }

    @Override
    public void setFilteredFocusedListShowAll(DisplayableType displayableType) {
        if (displayableType == DisplayableType.EVENT) {
            updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
            focusedFilteredDisplayables = filteredEvents;
        } else if (displayableType == DisplayableType.MODULE) {
            updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
            focusedFilteredDisplayables = filteredModules;
        }
    }


    //=========== Filtered Person List Accessors =============================================================

    @Override
    public void updateFilteredDisplayableList(Predicate<Displayable> predicate) {
        requireNonNull(predicate);
        FilteredList<? extends Displayable> filteredList = new FilteredList<>(focusedFilteredDisplayables, predicate);
        focusedFilteredDisplayables = filteredList;
    }

    public Object[] getFilteredDisplayableList(Predicate<Displayable> predicate) {
        requireNonNull(predicate);
        updateFilteredDisplayableList(predicate);
        Object[] focusFilteredDisplayableCopy = getFilteredFocusedList().toArray();
        setFilteredFocusedList(getCurrentDisplayableType());
        return focusFilteredDisplayableCopy;
    }

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of {@code
     * versionedAddressBook}
     */
    @Override
    public ObservableList<Event> getFilteredEventList() {
        return filteredEvents;
    }

    /**
     * retrieve selected event (but the method is not called here )
     *
     * @return
     */
    @Override
    public Displayable getFocusedDisplayable() {
        return focusedDisplayable;
    }

    @Override
    public boolean isSameFocusedDisplayable(Displayable displayable) {
        return this.focusedDisplayable.equals(displayable);
    }

    @Override
    public void updateFilteredEventList(Predicate<Event> predicate) {
        requireNonNull(predicate);
        filteredEvents.setPredicate(predicate);
        currentDisplayableType = DisplayableType.EVENT;
    }

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of {@code
     * versionedAddressBook}
     */
    @Override
    public ObservableList<Module> getFilteredModuleList() {
        return filteredModules;
    }

    @Override
    public void updateFilteredModuleList(Predicate<Module> predicate) {
        requireNonNull(predicate);
        filteredModules.setPredicate(predicate);
        currentDisplayableType = DisplayableType.MODULE;
    }

    @Override
    public ObservableList<? extends Displayable> getFilteredFocusedList() {
        return focusedFilteredDisplayables;
    }


    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return modulo.equals(other.modulo)
                && userPrefs.equals(other.userPrefs)
                && filteredEvents.equals(other.filteredEvents)
                && filteredModules.equals(other.filteredModules);
    }

    @Override
    public String checkCurrentCalendar() {
        List<Module> modules = modulo.getModuleList();
        List<Event> events = modulo.getEventList();
        StringBuilder sb = new StringBuilder();
        sb.append("Modules: ");
        sb.append("\n");
        for (Module eachModule : modules) {
            sb.append(eachModule.toDebugString());
            sb.append("\n");
        }

        sb.append("Events: ");
        sb.append("\n");
        for (Event eachEvent : events) {
            sb.append(eachEvent.toDebugString());
            sb.append("\n");
        }
        return sb.toString();
    }

}

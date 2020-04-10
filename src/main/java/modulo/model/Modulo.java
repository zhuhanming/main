package modulo.model;

import static java.util.Objects.requireNonNull;
import static modulo.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;
import modulo.model.event.Event;
import modulo.model.event.UniqueEventList;
import modulo.model.module.AcademicYear;
import modulo.model.module.Module;
import modulo.model.module.ModuleCode;
import modulo.model.module.UniqueModuleList;

/**
 * Wraps all data at the calendar level Duplicates are not allowed (by .isSameModule comparison)
 */
public class Modulo implements ReadOnlyModulo {

    private final UniqueEventList events;
    private final UniqueModuleList modules;

    /*
     *  The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     *  between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     *  Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *  among constructors.
     */
    {
        events = new UniqueEventList();
        modules = new UniqueModuleList();
    }

    public Modulo() {
    }

    /**
     * Creates a Modulo using the items in the {@code toBeCopied}
     */
    public Modulo(ReadOnlyModulo toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the module list with {@code modules}. {@code modules} must not contain duplicate
     * modules.
     */
    public void setModules(List<Module> modules) {
        this.modules.setModules(modules);
    }

    /**
     * Replaces the contents of the event list with {@code events}. {@code events} must not contain duplicate events.
     */
    public void setEvents(List<Event> events) {
        this.events.setEvents(events);
    }

    /**
     * Resets the existing data of this {@code Modulo} with {@code newData}.
     */
    public void resetData(ReadOnlyModulo newData) {
        requireNonNull(newData);

        setEvents(newData.getEventList());
        setModules(newData.getModuleList());
    }

    // module-level operations

    /**
     * Returns true if a module with the given {@code moduleCode} and {@code academicYear} exists in Modulo.
     */
    public boolean hasModule(ModuleCode moduleCode, AcademicYear academicYear) {
        requireAllNonNull(moduleCode, academicYear);
        return modules.contains(moduleCode, academicYear);
    }

    /**
     * Adds a module to Modulo using the {@code moduleCode} and {@code academicYear}.
     *
     * @param moduleCode   Module code of the module.
     * @param academicYear Academic year.
     */
    public void addModule(ModuleCode moduleCode, AcademicYear academicYear) {
        modules.addModule(moduleCode, academicYear);
    }

    /**
     * Returns an optional containing the module required.
     *
     * @param moduleCode   Module code of the module required.
     * @param academicYear Academic year of the module.
     * @return Optional containing the module.
     */
    public Optional<Module> getModule(ModuleCode moduleCode, AcademicYear academicYear) {
        return modules.getModule(moduleCode, academicYear);
    }

    /**
     * Adds a module to Modulo. The module must not already exist in Modulo.
     */
    public void addModuleFromStorage(Module module) {
        modules.addModule(module.getModuleCode(), module.getAcademicYear());
        Module actualModule = modules.getModule(module.getModuleCode(), module.getAcademicYear()).get();
        for (Event event : module.getEvents()) {
            Event actualEvent = new Event(event.getName(), event.getEventType(), event.getEventStart(),
                    event.getEventEnd(), actualModule, event.getLocation(), event.getDeadlines());
            actualModule.addEvent(actualEvent);
            addEvent(actualEvent);
        }
    }

    /**
     * Replaces the given module {@code target} in the list with {@code editedModule}. {@code target} must exist in
     * Modulo. The module identity of {@code editedModule} must not be the same as another existing module in Modulo.
     */
    public void setModule(Module target, Module editedModule) {
        requireNonNull(editedModule);

        modules.setModule(target, editedModule);
    }

    /**
     * Removes {@code key} from {@code Modulo}. {@code key} must exist in {@code Modulo}.
     */
    public void removeModule(Module key) {
        modules.remove(key);
    }


    // event-level operations

    /**
     * Replaces the given event {@code target} in the list with {@code editedEvent}. {@code target} must exist in
     * Modulo. The event identity of {@code editedEvent} must not be the same as another existing event in Modulo.
     */
    public void setEvent(Event target, Event editedEvent) {
        requireNonNull(editedEvent);
        events.setEvent(target, editedEvent);
    }

    /**
     * Returns true if an event with the same identity as {@code event} exists in Modulo.
     */
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        // System.out.println("Calendar item !!!!!!!!!!!!!!!"+ calendarItem);
        return events.contains(event);
    }

    /**
     * Adds an event to Modulo. The event must not already exist in Modulo.
     */
    public void addEvent(Event event) {
        events.add(event);
    }


    /**
     * Removes {@code key} from this {@code Modulo}. {@code key} must exist in Modulo.
     */
    public void removeEvent(Event key) {
        events.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return events.asUnmodifiableObservableList().size() + " events "
                + modules.asUnmodifiableObservableList().size() + " modules";
        // TODO: refine later
    }

    @Override
    public ObservableList<Event> getEventList() {
        return events.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Module> getModuleList() {
        return modules.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Modulo // instanceof handles nulls
                && events.equals(((Modulo) other).events))
                && modules.equals(((Modulo) other).modules);
    }

    @Override
    public int hashCode() {
        return events.hashCode();
    }
}

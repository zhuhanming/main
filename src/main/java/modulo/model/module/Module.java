package modulo.model.module;

import static modulo.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;

import modulo.model.Name;
import modulo.model.displayable.Displayable;
import modulo.model.event.Event;


/**
 * Represents a module in Modulo. Guarantees: details are present and not null, field values are validated, immutable.
 * <p>
 * <p>
 * TODO: The current implementation results in 2 sources of truth for events. May need to remove events under modules.
 */
public class Module implements Displayable {

    // Identity Fields
    private ModuleCode moduleCode;
    private Name name;
    private AcademicYear academicYear;

    // Non-identity fields
    private String description;

    // Data fields
    private List<Event> events;

    /**
     * Constructs a {@code Module} with a given module code, name, academic year and description. An empty list of
     * events will be created.
     *
     * @param moduleCode   Module code.
     * @param name         Name of module.
     * @param academicYear Academic year of the module.
     * @param description  Description of the module.
     */
    public Module(ModuleCode moduleCode, Name name, AcademicYear academicYear, String description) {
        requireAllNonNull(moduleCode, name, academicYear, description);
        this.moduleCode = moduleCode;
        this.name = name;
        this.academicYear = academicYear;
        this.description = description;
        this.events = new ArrayList<>();
    }

    /**
     * Constructs a {@code Module} with a given module code, name, academic year, description, and an existing list of
     * events.
     *
     * @param moduleCode   Module code.
     * @param name         Name of module.
     * @param academicYear Academic year of the module.
     * @param description  Description of the module.
     * @param events       List of events for this module.
     */
    public Module(ModuleCode moduleCode, Name name, AcademicYear academicYear, String description,
                  List<Event> events) {
        requireAllNonNull(moduleCode, name, academicYear, description);
        this.moduleCode = moduleCode;
        this.name = name;
        this.academicYear = academicYear;
        this.description = description;
        this.events = new ArrayList<>();
        this.events.addAll(events);
    }

    public ModuleCode getModuleCode() {
        return moduleCode;
    }

    public Name getName() {
        return name;
    }

    public List<Event> getEvents() {
        return events;
    }

    public AcademicYear getAcademicYear() {
        return academicYear;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Adds an event to the list of events under this module.
     *
     * @param event Event to add.
     */
    public void addEvent(Event event) {
        events.add(event);
    }

    /**
     * Removes an event from the list of events under this module.
     *
     * @param event Event to remove.
     */
    public void deleteEvent(Event event) {
        events.remove(event);
    }

    /**
     * Returns true if both modules have the same identity. This is a loose check based on module code, name and
     * academic year.
     *
     * @param otherModule Other module to compare with.
     * @return boolean value denoting whether the two modules are equal.
     */
    public boolean isSameModule(Module otherModule) {
        if (otherModule == this) {
            return true;
        } else {
            return otherModule.getModuleCode().equals(this.getModuleCode())
                    && otherModule.getAcademicYear().equals(this.getAcademicYear());
        }
    }


    /**
     * Returns true if both modules have the same identity and data fields. This defines a stronger notion of equality
     * between two modules.
     *
     * @param other Other object to check with.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Module)) {
            return false;
        }

        Module otherModule = (Module) other;
        return otherModule.getModuleCode().equals(getModuleCode())
                && otherModule.getName().equals(getName())
                && otherModule.getAcademicYear().equals(getAcademicYear())
                && otherModule.getDescription().equals(getDescription())
                && otherModule.getEvents().equals(getEvents());

    }

    /**
     * Returns a string for debugging purposes.
     *
     * @return String for debugging purposes.
     */
    public String toDebugString() {
        return moduleCode + " | " + getAcademicYear().getStartDate().toString() + " | "
                + getAcademicYear().getEndDate().toString();
    }

    @Override
    public String toString() {
        return moduleCode.toString() + " | " + name.toString() + " | " + academicYear.toModuleCardFormat();
    }
}

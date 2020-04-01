package modulo.model.module;

import static modulo.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;

import modulo.model.Name;
import modulo.model.displayable.Displayable;
import modulo.model.event.Event;


/**
 * Represents a module in Modulo. Guarantees: details are present and not null, field values are validated, immutable.
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

    public Module(ModuleCode moduleCode, Name name, AcademicYear academicYear, String description) {
        requireAllNonNull(moduleCode, name, academicYear, description);
        this.moduleCode = moduleCode;
        this.name = name;
        this.academicYear = academicYear;
        this.description = description;
        this.events = new ArrayList<>();
    }

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

    public Module() {
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

    public void addEvent(Event event) {
        events.add(event);
    }

    public void deleteEvent(Event event) {
        events.remove(event);
    }

    /**
     * Returns true if both modules have the same identity.
     *
     * @param otherModule Other module to compare with.
     * @return boolean value denoting whether the two modules are equal.
     */
    public boolean isSameModule(Module otherModule) {
        if (otherModule == this) {
            return true;
        } else {
            return otherModule.getModuleCode().equals(this.getModuleCode())
                    && otherModule.getName().equals(this.getName())
                    && otherModule.getAcademicYear().toString().equals(this.getAcademicYear().toString());
        }
    }


    /**
     * Matches the module based on the loose criteria of naming.
     * <p>TODO: Add support for academic year matching subsequently.
     *
     * @param otherModule Module to compare with
     * @return boolean value on whether this matches
     */
    public boolean matchModule(Module otherModule) {
        return this.moduleCode.equals(otherModule.getModuleCode());
    }

    /**
     * Returns true if both modules have the same identity and data fields. This defines a stronger notion of equality
     * between two modules.
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

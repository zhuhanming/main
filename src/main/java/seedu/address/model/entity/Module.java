package seedu.address.model.entity;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * Represents a module in Modulo.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Module {

    // Identity Fields
    private String moduleCode;
    private LocalDate startDate;
    private LocalDate endDate;

    // Data fields
    private List<Event> events;

    public Module(String moduleCode, LocalDate startDate, LocalDate endDate) {
        requireAllNonNull(moduleCode, startDate, endDate);
        this.moduleCode = moduleCode;
        this.startDate = startDate;
        this.endDate = endDate;
        this.events = new ArrayList<>();
    }

    public Module() {
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
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
                    && otherModule.getStartDate().equals(this.getStartDate())
                    && otherModule.getEndDate().equals(this.getEndDate());
        }
    }


    /**
     * Matches the module based on the loose criteria of naming.
     *
     * @param otherModule Module to compare with
     * @return boolean value on whether this matches
     */
    public boolean matchModule(Module otherModule) {
        return this.moduleCode.equals(otherModule.getModuleCode());
    }

    /**
     * Returns true if both modules have the same identity and data fields.
     * This defines a stronger notion of equality between two modules.
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
                && otherModule.getStartDate().equals(getStartDate())
                && otherModule.getEndDate().equals(getEndDate())
                && otherModule.getEvents().equals(getEvents());
    }

    /**
     * Returns a string for debugging purposes.
     *
     * @return String for debugging purposes.
     */
    public String toDebugString() {
        return moduleCode + " | " + (startDate != null ? startDate.toString() : "null") + " | "
                + (endDate != null ? endDate.toString() : "null");
    }
}

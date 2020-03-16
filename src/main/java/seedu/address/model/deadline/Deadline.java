package seedu.address.model.deadline;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;

import seedu.address.model.Name;
import seedu.address.model.event.Event;
import seedu.address.model.module.Module;


/**
 * Represents a deadline in Modulo. Guarantees: details are present and not null, field values are validated, fields are
 * immutable.
 */
public class Deadline {

    // Identity fields
    private Name name;
    private LocalDateTime dueTime;
    private Event parentEvent;

    // Data fields
    private boolean isCompleted;

    public Deadline(Name name, Event parentEvent) {
        requireAllNonNull(name, parentEvent);
        this.name = name;
        this.parentEvent = parentEvent;
        this.dueTime = parentEvent.getEventStart();
        this.isCompleted = false;
    }

    public Deadline(Name name, Event parentEvent, boolean isCompleted) {
        requireAllNonNull(name, parentEvent);
        this.name = name;
        this.parentEvent = parentEvent;
        this.dueTime = parentEvent.getEventStart();
        this.isCompleted = isCompleted;
    }

    public Name getName() {
        return name;
    }

    public Event getParentEvent() {
        return parentEvent;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public Module getModule() {
        return parentEvent.getParentModule();
    }

    /**
     * Returns a new Deadline with the new Event as its parentEvent to maintain immutability.
     *
     * @param newParentEvent New event to set as parent.
     * @return New Deadline.
     */
    public Deadline setParentEvent(Event newParentEvent) {
        return new Deadline(name, newParentEvent, isCompleted);
    }

    /**
     * Returns a new completed Deadline to maintain immutability.
     *
     * @return Completed Deadline.
     */
    public Deadline completeDeadline() {
        return new Deadline(name, parentEvent, true);
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    /**
     * Returns a string for debugging purposes.
     *
     * @return String for debugging purposes.
     */
    public String toDebugString() {
        return getModule().getModuleCode() + " | " + "DEADLINE" + " | "
                + name + " || " + parentEvent.toDebugString();
    }

    /**
     * Returns true if both deadlines have the same identity.
     *
     * @param otherDeadline The deadline to compare to.
     * @return boolean value denoting whether the two deadlines are the same deadline.
     */
    public boolean isSameDeadline(Deadline otherDeadline) {
        if (otherDeadline == this) {
            return true;
        }

        return otherDeadline.getName().equals(getName())
                && otherDeadline.getParentEvent().equals(getParentEvent());
    }

    /**
     * Returns true if both deadlines have the same identity and data fields. This defines a stronger notion of equality
     * between two deadlines.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Deadline)) {
            return false;
        }

        Deadline otherDeadline = (Deadline) other;
        return otherDeadline.getName().equals(getName())
                && otherDeadline.getParentEvent().equals(getParentEvent())
                && otherDeadline.getModule().equals(getModule())
                && otherDeadline.isCompleted() == isCompleted();
    }
}

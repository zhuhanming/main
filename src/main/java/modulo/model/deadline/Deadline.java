package modulo.model.deadline;

import static modulo.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;

import modulo.model.Name;
import modulo.model.displayable.Displayable;
import modulo.model.event.Event;


/**
 * Represents a deadline in Modulo. Guarantees: details are present and not null, field values are validated, fields are
 * immutable.
 */
public class Deadline implements Displayable {

    // Identity fields
    private final Name name;
    private final LocalDateTime dueTime;

    // Data fields
    private boolean isCompleted;

    /**
     * Constructs a deadline with a given name and parent event to get the due time from.
     *
     * @param name        Name of deadline.
     * @param parentEvent Parent event.
     */
    public Deadline(Name name, Event parentEvent) {
        requireAllNonNull(name, parentEvent);
        this.name = name;
        this.dueTime = parentEvent.getEventStart();
        this.isCompleted = false;
    }

    /**
     * Constructs a deadline with a given name, due time and completion status.
     *
     * @param name        Name of deadline.
     * @param dueTime     Due time of deadline.
     * @param isCompleted Completion status.
     */
    public Deadline(Name name, LocalDateTime dueTime, boolean isCompleted) {
        requireAllNonNull(name, dueTime);
        this.name = name;
        this.dueTime = dueTime;
        this.isCompleted = isCompleted;
    }

    public Name getName() {
        return name;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public LocalDateTime getDueTime() {
        return dueTime;
    }

    /**
     * Sets the completion status of the deadline. Unfortunately, this makes the deadline an immutable object.
     *
     * @param completed New completion status of the deadline.
     */
    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    /**
     * Returns true if both deadlines have the same identity. This is not a strong check.
     *
     * @param otherDeadline The deadline to compare to.
     * @return boolean value denoting whether the two deadlines are the same deadline.
     */
    public boolean isSameDeadline(Deadline otherDeadline) {
        if (otherDeadline == this) {
            return true;
        }
        return otherDeadline != null
                && otherDeadline.getName().equals(getName());
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
                && otherDeadline.getDueTime().equals(getDueTime())
                && otherDeadline.isCompleted() == isCompleted();
    }

    @Override
    public String toString() {
        return this.name.toString();
    }
}

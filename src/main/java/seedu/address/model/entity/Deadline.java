package seedu.address.model.entity;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;

/**
 * Represents a deadline in Modulo.
 * Guarantees: details are present and not null, field values are validated, fields are immutable.
 */
public class Deadline extends CalendarItem {

    // Identity fields
    private CalendarItemName deadlineName;
    private LocalDateTime dueTime;
    private Event parentEvent;

    // Data fields
    private boolean isCompleted;

    public Deadline(CalendarItemName deadlineName, Event parentEvent) {
        requireAllNonNull(deadlineName, parentEvent);
        this.deadlineName = deadlineName;
        this.parentEvent = parentEvent;
        this.dueTime = parentEvent.getEventStart();
        this.isCompleted = false;
    }

    public Deadline(CalendarItemName deadlineName, Event parentEvent, boolean isCompleted) {
        requireAllNonNull(deadlineName, parentEvent);
        this.deadlineName = deadlineName;
        this.parentEvent = parentEvent;
        this.dueTime = parentEvent.getEventStart();
        this.isCompleted = isCompleted;
    }

    public CalendarItemName getDeadlineName() {
        return deadlineName;
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
        return new Deadline(deadlineName, newParentEvent, isCompleted);
    }

    /**
     * Returns a new completed Deadline to maintain immutability.
     *
     * @return Completed Deadline.
     */
    public Deadline completeDeadline() {
        return new Deadline(deadlineName, parentEvent, true);
    }

    /**
     * Returns a string for debugging purposes.
     *
     * @return String for debugging purposes.
     */
    public String toDebugString() {
        return getModule().getModuleCode() + " | " + CalendarItemType.DEADLINE + " | "
                + deadlineName + " || " + parentEvent.toDebugString();
    }

    @Override
    public boolean isSameCalendarItem(CalendarItem otherCalendarItem) {

        if (otherCalendarItem == this) {
            System.out.println(toDebugString() + " " + otherCalendarItem.toDebugString());
            return true;
        }

        if (otherCalendarItem.getCalendarItemType() != CalendarItemType.DEADLINE) {
            return false;
        }

        Deadline otherDeadline = (Deadline) otherCalendarItem;

        return otherDeadline.getDeadlineName().equals(getDeadlineName())
                && otherDeadline.getParentEvent().equals(getParentEvent());
    }

    @Override
    public String getItemName() {
        return deadlineName.toString();
    }

    @Override
    public CalendarItemType getCalendarItemType() {
        return CalendarItemType.DEADLINE;
    }
}

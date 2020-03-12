package seedu.address.model.entity;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an event in Modulo.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event extends CalendarItem {

    // Identity Fields
    private CalendarItemName eventName;
    private EventType eventType;
    private LocalDateTime eventStart;
    private LocalDateTime eventEnd;
    private Module parentModule;

    // Data Fields
    private List<Deadline> deadlines;
    private boolean isOver;

    public Event(CalendarItemName eventName, EventType eventType, LocalDateTime eventStart,
                 LocalDateTime eventEnd, Module parentModule) {
        requireAllNonNull(eventName, eventStart, eventEnd);
        this.eventName = eventName;
        this.eventType = eventType;
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
        this.parentModule = parentModule;
        this.isOver = LocalDateTime.now().isAfter(eventEnd);
        this.deadlines = new ArrayList<>();
        this.deadlines.add(new Deadline(this.eventType.getDefaultDeadlineDescription(), this));
    }

    public EventType getEventType() {
        return eventType;
    }

    public CalendarItemName getEventName() {
        return eventName;
    }

    public List<Deadline> getDeadlines() {
        return deadlines;
    }

    public boolean getIsOver() {
        return isOver;
    }

    public Module getParentModule() {
        return parentModule;
    }

    public Event setParentModule(Module newParentModule) {
        return new Event(eventName, eventType, eventStart, eventEnd, newParentModule);
    }

    public LocalDateTime getEventStart() {
        return eventStart;
    }

    public LocalDateTime getEventEnd() {
        return eventEnd;
    }

    /**
     * Returns true if both events have the same identity.
     *
     * @param otherEvent The event to compare to.
     * @return boolean value denoting whether the two events are the same event.
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getEventName().equals(getEventName())
                && otherEvent.getParentModule().equals(getParentModule())
                && otherEvent.getEventType().equals(getEventType());
    }

    @Override
    public Module getModule() {
        return parentModule;
    }


    /**
     * Returns true if both events have the same identity and data fields.
     * This defines a stronger notion of equality between two events.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return otherEvent.getEventName().equals(getEventName())
                && otherEvent.getParentModule().equals(getParentModule())
                && otherEvent.getDeadlines().equals(getDeadlines())
                && otherEvent.getEventEnd().equals(getEventEnd())
                && otherEvent.getEventStart().equals(getEventStart())
                && otherEvent.getIsOver() == getIsOver()
                && otherEvent.getEventType().equals(getEventType());
    }

    /**
     * Returns a string for debugging purposes.
     *
     * @return String for debugging purposes.
     */
    public String toDebugString() {
        return (getModule() == null ? "null" : getModule().getModuleCode()) + " | "
                + eventType + " | " + eventName + " | " + eventStart + " | " + eventEnd;
    }

    @Override
    public boolean isSameCalendarItem(CalendarItem otherCalendarItem) {
        System.out.println("Comparing: " + toDebugString() + " **** " + otherCalendarItem.toDebugString());
        if (otherCalendarItem == this) {
            System.out.println("SAME EVENT");
            return true;
        }

        if (otherCalendarItem.getCalendarItemType() != CalendarItemType.EVENT) {
            return false;
        }

        Event otherEvent = (Event) otherCalendarItem;
        return this.eventName.equals(otherEvent.getEventName())
                && this.eventType.equals(otherEvent.getEventType())
                && this.getModule().matchModule(otherEvent.getModule());
    }

    @Override
    public String getItemName() {
        return eventName.toString();
    }

    @Override
    public CalendarItemType getCalendarItemType() {
        return CalendarItemType.EVENT;
    }
}


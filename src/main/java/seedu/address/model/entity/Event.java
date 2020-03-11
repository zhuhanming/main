package seedu.address.model.entity;

import seedu.address.model.person.Person;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Event extends CalendarItem {

    private CalendarItemName eventName;
    // List of Enum class
    private Enum eventType;
    private List<Deadline> deadlines;
    private boolean isOver;
    private LocalDateTime eventStart;
    private LocalDateTime eventEnd;
    private Module parentModule;


    public Event() {
    }

    public Event(CalendarItemName eventName, Enum eventType, LocalDateTime eventStart, LocalDateTime eventEnd) {
        this.eventName = eventName;
        this.eventType = eventType;
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
        this.deadlines = new ArrayList<>();
    }


    public Event(CalendarItemName eventName,Module module) {
        this.eventName = eventName;
        this.parentModule = module;
        this.deadlines = new ArrayList<>();
    }

    public Event(CalendarItemName eventName) {
        this.eventName = eventName;
    }


    /**
     * Returns true if a given string is a valid name.
     */
//    public static boolean isValidEvent(CalendarItemName test) {
//        return test.matches(VALIDATION_REGEX);
//    }



    public Enum getEventType() {
        return eventType;
    }

    public void setEventType(Enum eventType) {
        this.eventType = eventType;
    }

    public CalendarItemName getEventName() {
        return eventName;
    }

    public void setEventName(CalendarItemName eventName) {
        this.eventName = eventName;
    }

    public List<Deadline> getDeadlines() {
        return deadlines;
    }

    public void setDeadlines(List<Deadline> deadlines) {
        this.deadlines = deadlines;
    }

    public boolean getIsOver() {
        return isOver;
    }

    public void setIsOver(boolean isOver) {
        isOver = isOver;
    }

    public Module getParentModule() {
        return parentModule;
    }

    public void setParentModule(Module module) {
        this.parentModule = module;
    }

    public LocalDateTime getEventStart() {
        return eventStart;
    }

    public void setEventStart(LocalDateTime eventStart) {
        this.eventStart = eventStart;
    }

    public LocalDateTime getEventEnd() {
        return eventEnd;
    }

    public void setEventEnd(LocalDateTime eventEnd) {
        this.eventEnd = eventEnd;
    }

    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getEventName().equals(getEventName())
                && (otherEvent.getParentModule().equals(getParentModule()));
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
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
@Override
public Module getModule() {
        return parentModule;
}
    public String toDebugString() {
        return (getModule() == null ? "null" : getModule().getModuleCode()) + " | " + eventType + " | " + eventName + " | " + eventStart + " | " + eventEnd;
//        getTags().forEach(builder::append);
    }

    @Override
    public boolean isSameCalendarItem(CalendarItem otherCalendarItem) {
        System.out.println("COmparing: " + toDebugString() + " **** " + otherCalendarItem.toDebugString());
        if (otherCalendarItem == this) {
            System.out.println("SAME EVENT");
            return true;
        }

        if (otherCalendarItem.getCalendarItemType() != CalendarItemType.EVENT) {
            return false;
        }

        Event otherEvent = (Event) otherCalendarItem;
        return otherCalendarItem != null &&
                this.eventName.equals(otherEvent.getEventName()) &&
                this.eventType.equals(otherEvent.getEventType()) &&
                this.eventStart.equals(otherEvent.getEventStart()) &&
                this.eventEnd.equals(otherEvent.getEventEnd()) &&
                this.getModule().matchModule(otherEvent.getModule());
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

